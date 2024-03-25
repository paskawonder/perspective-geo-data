package com.booking.perspective.media.service;

import com.booking.perspective.geo.Coordinates;
import com.booking.perspective.geo.GeoService;
import com.booking.perspective.geo.entity.GeoTreeNode;
import com.booking.perspective.media.MediaInfo;
import com.booking.perspective.media.MediaInfoRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MediaService {

    private final int searchRadius;
    private final GeoService geoService;
    private final FileService fileService;
    private final MediaInfoRepository mediaInfoRepository;
    
    @Autowired
    public MediaService(GeoService geoService, FileService fileService, MediaInfoRepository mediaInfoRepository) {
        this.searchRadius = 0;
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
        List<String> ids = new ArrayList<>();
        List<GeoTreeNode> nodes = List.of(node);
        Set<String> visited = new HashSet<>();
        while (!nodes.isEmpty()) {
            visited.addAll(nodes.stream().map(GeoTreeNode::getId).toList());
            List<GeoTreeNode> adjs = nodes.stream().flatMap(e -> e.getAdjs().stream())
                    .filter(e -> !visited.contains(e.getId())).toList();
            nodes = adjs;
        }
        return ids;
    }

}
