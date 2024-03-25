package com.booking.perspective.media.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    
    private final String mediaPath;
    
    @Autowired
    public FileService(@Value("${media.path}") String mediaPath) {
        this.mediaPath = mediaPath;
    }
    
    public void save(ByteArrayOutputStream outputStream, String id) {
        Path path = Path.of(mediaPath, id);
        try {
            byte[] base64 = Base64.encodeBase64(outputStream.toByteArray());
            Files.copy(new ByteArrayInputStream(base64), path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    public List<String> getAll() {
        return Arrays.stream(new File(mediaPath).listFiles()).filter(e -> e.isFile())
                .filter(e -> !e.isHidden()).map(e -> {
            try {
                return new String(Files.readAllBytes(e.toPath()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }).toList();
    }
    
}
