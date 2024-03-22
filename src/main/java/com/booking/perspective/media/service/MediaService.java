package com.booking.perspective.media.service;

import com.booking.perspective.geo.Coordinates;
import com.booking.perspective.geo.GeoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

    private final GeoService geoService;
    private final FileService fileService;
    
    @Autowired
    public MediaService(GeoService geoService, FileService fileService) {
        this.geoService = geoService;
        this.fileService = fileService;
    }

    public void create(MediaMeta meta, String fileId) {
        String locationId = geoService.resolve(meta.getCoordinates()).getId();
    }
    
    public List<String> get(Coordinates coordinates) {
        return fileService.getAll();
    }

}
