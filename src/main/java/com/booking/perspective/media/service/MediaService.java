package com.booking.perspective.media.service;

import com.booking.perspective.geo.Coordinates;
import com.booking.perspective.geo.GeoService;
import com.booking.perspective.geo.GeoUtils;
import com.booking.perspective.geo.entity.GeoTreeNode;
import com.booking.perspective.media.MediaInfo;
import com.booking.perspective.media.MediaInfoRepository;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        MediaInfo mediaInfo = new MediaInfo(id, meta.getCoordinates().getLat(), meta.getCoordinates().getLon(), geoLeafId);
        mediaInfoRepository.save(mediaInfo);
    }
    
    @Transactional
    public List<String> get(Coordinates coordinates) {
        GeoTreeNode leaf = geoService.navigateToLeaf(coordinates);
        List<String> ids = expand(coordinates, leaf);
        return fileService.get(ids);
    }
    
    private List<String> expand(Coordinates coordinates, GeoTreeNode node) {
        BigDecimal lat = coordinates.getLat(), lng = coordinates.getLon();
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
                        if (lng.compareTo(e.getLeftTopLon()) < 0) {
                            x = e.getLeftTopLon();
                        } else if (lng.compareTo(e.getRightBotLon()) > 0) {
                            x = e.getRightBotLon();
                        }
                        return Double.compare(geoUtils.dist(lat, lng, y, x), searchRadius) < 1;
                    }).toList();
        }
        return mediaInfoRepository.findByGeoLeafIdIn(visited).stream().filter(e -> Double.compare(geoUtils.dist(lat, lng, e.getLat(), e.getLon()), searchRadius) < 1).map(MediaInfo::getId).toList();
    }
    
}
