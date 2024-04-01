package com.booking.perspective.media.service;

import com.booking.perspective.media.FileRepository;
import java.util.Collection;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    
    private final FileRepository fileRepository;
    
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
    
    public void save(String id, byte[] bytes) {
        fileRepository.save(id, bytes);
    }
    
    public Map<String, String> getUrls(Collection<String> ids) {
        return fileRepository.getUrls(ids);
    }
    
}
