package com.booking.perspective.media.service;

import com.booking.perspective.geo.Coordinates;
import com.booking.perspective.geo.GeoService;
import com.booking.perspective.geo.GeoUtils;
import com.booking.perspective.geo.entity.GeoTreeNode;
import com.booking.perspective.media.MediaInfo;
import com.booking.perspective.media.MediaInfoRepository;
import com.booking.perspective.media.model.MediaResponse;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MediaService {

    private final double searchRadius;
    private final GeoService geoService;
    private final FileService fileService;
    private final MediaInfoRepository mediaInfoRepository;
    private final GeoUtils geoUtils;
    
    @Autowired
    public MediaService(@Value("${media.search.radius}") int searchRadius, GeoService geoService, FileService fileService, MediaInfoRepository mediaInfoRepository, GeoUtils geoUtils) {
        this.searchRadius = searchRadius;
        this.geoUtils = geoUtils;
        this.geoService = geoService;
        this.fileService = fileService;
        this.mediaInfoRepository = mediaInfoRepository;
    }

    @Transactional
    public void create(MediaMeta meta, String id) {
        String geoLeafId = geoService.navigateToLeaf(meta.getCoordinates()).getId();
        MediaInfo mediaInfo = new MediaInfo(id, meta.getCoordinates().getLat(), meta.getCoordinates().getLng(), geoLeafId);
        mediaInfoRepository.save(mediaInfo);
    }
    
    @Transactional
    public List<MediaResponse> get(Coordinates coordinates) {
        List<MediaInfo> medias = expand(coordinates, geoService.navigateToLeaf(coordinates));
        Map<String, Coordinates> coordinatesById = medias.stream().collect(Collectors.toMap(MediaInfo::getId, e -> new Coordinates(e.getLat(), e.getLng())));
        Map<String, String> payloads = fileService.get(coordinatesById.keySet());
        return payloads.entrySet().stream().map(e -> new MediaResponse(e.getValue(), coordinatesById.get(e.getKey()))).toList();
    }
    
    private List<MediaInfo> expand(Coordinates coordinates, GeoTreeNode node) {
        BigDecimal lat = coordinates.getLat(), lng = coordinates.getLng();
        List<GeoTreeNode> nodes = List.of(node);
        Set<String> visited = new HashSet<>();
        while (!nodes.isEmpty()) {
            visited.addAll(nodes.stream().map(GeoTreeNode::getId).toList());
            nodes = nodes.stream().flatMap(e -> e.getAdjs().stream())
                    .filter(e -> !visited.contains(e.getId()))
                    .filter(e -> {
                        BigDecimal y = lat;
                        if (lat.compareTo(e.getLeftTopLat()) > 0) {
                            y = e.getLeftTopLat();
                        } else if (lat.compareTo(e.getRightBotLat()) < 0) {
                            y = e.getRightBotLat();
                        }
                        BigDecimal x = lng;
                        if (lng.compareTo(e.getLeftTopLng()) < 0) {
                            x = e.getLeftTopLng();
                        } else if (lng.compareTo(e.getRightBotLng()) > 0) {
                            x = e.getRightBotLng();
                        }
                        return Double.compare(geoUtils.dist(lat, lng, y, x), searchRadius) < 1;
                    }).toList();
        }
        return mediaInfoRepository.findByGeoLeafIdIn(visited).stream().filter(e -> Double.compare(geoUtils.dist(lat, lng, e.getLat(), e.getLng()), searchRadius) < 1).toList();
    }
    
}
