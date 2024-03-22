package com.booking.perspective.media.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    
    private final String mediaPath;
    
    @Autowired
    public FileService(@Value("${media.path}") String mediaPath) {
        this.mediaPath = mediaPath;
    }
    
    public String save(MultipartFile file) {
        String fileId = UUID.randomUUID().toString();
        Path path = Path.of(mediaPath, fileId);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return fileId;
    }
    
}
