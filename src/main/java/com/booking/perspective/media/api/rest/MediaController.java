package com.booking.perspective.media.api.rest;

import com.booking.perspective.media.service.FileService;
import com.booking.perspective.media.service.MediaMeta;
import com.booking.perspective.media.service.MediaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
public class MediaController {
    
    private final FileService fileService;
    private final MediaService mediaService;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public MediaController(FileService fileService, MediaService mediaService, ObjectMapper objectMapper) {
        this.fileService = fileService;
        this.mediaService = mediaService;
        this.objectMapper = objectMapper;
    }
    
    @PostMapping
    public void uploadFile(@RequestParam("meta") String metaStr, @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        MediaMeta meta = objectMapper.readValue(metaStr, MediaMeta.class);
        String fileId = fileService.save(file);
        mediaService.create(meta, fileId);
    }
    
}
