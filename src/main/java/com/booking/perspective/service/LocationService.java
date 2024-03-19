package com.booking.perspective.service;

import com.booking.perspective.geo.entity.QuadTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    
    private final QuadTreeNode root;
    
    @Autowired
    public LocationService(QuadTreeNode root) {
        this.root = root;
    }

    public String save(double lat, double lon) {
        QuadTreeNode node = resolve(root, lat, lon);
        return String.valueOf(node.getId());
    }
    
    private QuadTreeNode resolve(QuadTreeNode root, double lat, double lon) {
        QuadTreeNode node = root;
        while (!node.getChilds().isEmpty()) {
            node = node.getChilds().stream()
                    .filter(e -> e.getLeftTopLat() >= lat && e.getLeftTopLon() >= lon && e.getRightBotLat() <= lat && e.getRightBotLat() <= lon)
                    .findAny().orElseThrow();
        }
        return node;
    }

}
