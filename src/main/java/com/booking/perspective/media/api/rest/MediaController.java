package com.booking.perspective.media.api.rest;

import com.booking.perspective.media.service.FileService;
import com.booking.perspective.media.service.MediaMeta;
import com.booking.perspective.media.service.MediaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public void create(@RequestParam("meta") String metaStr, @RequestParam("file") MultipartFile file) throws IOException {
        MediaMeta meta = objectMapper.readValue(metaStr, MediaMeta.class);
        ByteArrayOutputStream thumbnail = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream())
                .height(200).width(200)
                .outputQuality(0.5).toOutputStream(thumbnail);
        String id = UUID.randomUUID().toString();
        fileService.save(thumbnail, id);
        mediaService.create(meta, id);
    }
    
    @GetMapping
    public List<String> get() {
        return mediaService.get(null);
    }
    
}
