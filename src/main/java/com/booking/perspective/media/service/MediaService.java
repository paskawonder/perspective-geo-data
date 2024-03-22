package com.booking.perspective.media.service;

import com.booking.perspective.geo.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

    private final GeoService geoService;
    
    @Autowired
    public MediaService(GeoService geoService) {
        this.geoService = geoService;
    }

    public void create(MediaMeta meta, String fileId) {
        String locationId = geoService.resolve(meta.getCoordinates()).getId();
    }

}
