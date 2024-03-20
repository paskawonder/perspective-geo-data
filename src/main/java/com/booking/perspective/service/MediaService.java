package com.booking.perspective.service;

import com.booking.perspective.api.rest.model.MediaRequest;
import com.booking.perspective.geo.GeoService;
import com.booking.perspective.model.media.MediaFile;
import com.booking.perspective.model.media.MediaInfo;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MediaService {

    private final GeoService geoService;
    
    @Autowired
    public MediaService(GeoService geoService) {
        this.geoService = geoService;
    }

    public void create(MediaRequest mediaRequest) {
        String locationId = geoService.resolve(new BigDecimal(mediaRequest.lat()), new BigDecimal(mediaRequest.lon())).getId();
        String fileId = UUID.randomUUID().toString();
        MediaFile mediaFile = new MediaFile(fileId, mediaRequest.payload());
        MediaInfo mediaInfo = new MediaInfo(fileId, mediaRequest.userId(), mediaRequest.lat(), mediaRequest.lon(), mediaRequest.utcDateTime());
    }

}
