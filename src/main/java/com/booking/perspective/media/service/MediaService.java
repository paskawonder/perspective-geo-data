package com.booking.perspective.media.service;

import com.booking.perspective.geo.Coordinates;
import com.booking.perspective.geo.GeoService;
import com.booking.perspective.geo.GeoUtils;
import com.booking.perspective.geo.entity.GeoTreeNode;
import com.booking.perspective.media.MediaInfo;
import com.booking.perspective.media.MediaInfoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        List<String> ids = new ArrayList<>();
        List<GeoTreeNode> nodes = List.of(node);
        Set<String> visited = new HashSet<>();
        while (!nodes.isEmpty()) {
            List<String> nIds = nodes.stream().map(GeoTreeNode::getId).toList();
            visited.addAll(nIds);
            ids.addAll(mediaInfoRepository.findByGeoLeafIdIn(nIds).stream().map(MediaInfo::getId).toList());
            List<GeoTreeNode> adjs = nodes.stream().flatMap(e -> e.getAdjs().stream())
                    .filter(e -> !visited.contains(e.getId()))
                    .filter(e -> {
                        BigDecimal minDistLat = e.getLeftTopLat();
                        if (lat.subtract(e.getLeftTopLat().abs()).compareTo(lat.subtract(e.getRightBotLat()).abs()) > 0) {
                            minDistLat = e.getRightBotLat();
                        }
                        BigDecimal minDistLng = e.getLeftTopLon();
                        if (lng.subtract(e.getLeftTopLon()).abs().compareTo(lng.subtract(e.getRightBotLon()).abs()) > 0) {
                            minDistLng = e.getRightBotLon();
                        }
                        return Double.compare(geoUtils.dist(lat.doubleValue(), lng.doubleValue(), minDistLat.doubleValue(), minDistLng.doubleValue()), searchRadius) < 1;
                    }).toList();
            nodes = adjs;
        }
        return ids;
    }
    
}
