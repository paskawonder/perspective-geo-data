package com.booking.perspective.service;

import com.booking.perspective.api.rest.model.MediaRequest;
import com.booking.perspective.model.media.MediaFile;
import com.booking.perspective.model.media.MediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MediaService {

    private final LocationService locationService;
    
    @Autowired
    public MediaService(LocationService locationService) {
        this.locationService = locationService;
    }

    public void create(MediaRequest mediaRequest) {
        String locationId = locationService.save(mediaRequest.lat(), mediaRequest.lon());
        String fileId = UUID.randomUUID().toString();
        MediaFile mediaFile = new MediaFile(fileId, mediaRequest.payload());
        MediaInfo mediaInfo = new MediaInfo(fileId, mediaRequest.userId(), mediaRequest.lat(), mediaRequest.lon(), mediaRequest.utcDateTime());
    }

}
