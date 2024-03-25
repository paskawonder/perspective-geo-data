package com.booking.perspective.media.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    
    private final String mediaStoragePath;
    
    @Autowired
    public FileService(@Value("${media.storage.path}") String mediaStoragePath) {
        this.mediaStoragePath = mediaStoragePath;
    }
    
    public void save(ByteArrayOutputStream outputStream, String id) {
        Path path = Path.of(mediaStoragePath, id);
        try {
            byte[] base64 = Base64.encodeBase64(outputStream.toByteArray());
            Files.copy(new ByteArrayInputStream(base64), path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    public List<String> get(Collection<String> ids) {
        return ids.stream().map(id -> Path.of(mediaStoragePath, id)).map(path -> {
            try {
                return Files.readString(path);
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }).toList();
    }
    
}
