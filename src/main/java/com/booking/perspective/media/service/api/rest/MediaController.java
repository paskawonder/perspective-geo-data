package com.booking.perspective.media.service.api.rest;

import com.booking.perspective.media.service.MediaMeta;
import com.booking.perspective.media.service.FileService;
import com.booking.perspective.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
public class MediaController {
    
    private final FileService fileService;
    private final MediaService mediaService;
    
    @Autowired
    public MediaController(FileService fileService, MediaService mediaService) {
        this.fileService = fileService;
        this.mediaService = mediaService;
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestPart MediaMeta meta, @RequestPart MultipartFile file) {
        String fileId = fileService.save(file);
        mediaService.create(meta, fileId);
    }
    
}
