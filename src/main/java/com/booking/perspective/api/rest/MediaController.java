package com.booking.perspective.api.rest;

import com.booking.perspective.api.rest.model.MediaRequest;
import com.booking.perspective.geo.entity.QuadTreeNode;
import com.booking.perspective.geo.repository.QuadTreeNodeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    
    @Transactional
    @GetMapping
    public List<String> create(MediaRequest mediaRequest) {
        List<String> list = new ArrayList<>();
        for (QuadTreeNode e: quadTreeNodeRepository.findAll()) {
            list.add(e.getLeftTopLat() + " " + e.getLeftTopLon() + " " + e.getRightBotLat() + " " + e.getRightBotLon());
        }
        return list;
    }

}
