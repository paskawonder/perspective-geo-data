package com.booking.perspective.api.rest;

import com.booking.perspective.api.rest.model.MediaRequest;
import com.booking.perspective.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;

public class MediaController {
    
    private final MediaService mediaService;
    
    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }
    
    public void create(MediaRequest mediaRequest) {
        mediaService.create(mediaRequest);
    }

}
