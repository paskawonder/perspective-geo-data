package com.booking.perspective.media.service;

import com.booking.perspective.geo.Coordinates;
import com.booking.perspective.geo.GeoService;
import com.booking.perspective.geo.entity.GeoTreeNode;
import com.booking.perspective.media.MediaInfo;
import com.booking.perspective.media.MediaInfoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

    private final GeoService geoService;
    private final FileService fileService;
    private final MediaInfoRepository mediaInfoRepository;
    
    @Autowired
    public MediaService(GeoService geoService, FileService fileService, MediaInfoRepository mediaInfoRepository) {
        this.geoService = geoService;
        this.fileService = fileService;
        this.mediaInfoRepository = mediaInfoRepository;
    }

    public void create(MediaMeta meta, String id) {
        String geoLeafId = geoService.navigateToLeaf(meta.getCoordinates()).getId();
        MediaInfo mediaInfo = new MediaInfo(id, meta.getCoordinates().getLat(), meta.getCoordinates().getLon(), geoLeafId);
        mediaInfoRepository.save(mediaInfo);
    }
    
    public List<String> get(Coordinates coordinates) {
        GeoTreeNode leaf = geoService.navigateToLeaf(coordinates);
        List<String> ids = expand(coordinates, leaf);
        return fileService.get(ids);
    }

}
