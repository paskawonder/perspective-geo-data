package com.booking.perspective.api.rest;

import com.booking.perspective.api.rest.model.MediaRequest;
import com.booking.perspective.geo.repository.QuadTreeNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
public class MediaController {
    
    private final QuadTreeNodeRepository quadTreeNodeRepository;
    
    @Autowired
    public MediaController(QuadTreeNodeRepository quadTreeNodeRepository) {
        this.quadTreeNodeRepository = quadTreeNodeRepository;
    }
    
    @GetMapping
    public Object create(MediaRequest mediaRequest) {
        return quadTreeNodeRepository.findAll();
    }

}
