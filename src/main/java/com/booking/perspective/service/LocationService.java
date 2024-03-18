package com.booking.perspective.service;

import com.booking.perspective.geo.entity.RectTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    
    private final RectTreeNode root;
    
    @Autowired
    public LocationService(RectTreeNode root) {
        this.root = root;
    }

    public String save(double lat, double lon) {
        RectTreeNode node = resolve(root, lat, lon);
        return String.valueOf(node.getId());
    }
    
    private RectTreeNode resolve(RectTreeNode root, double lat, double lon) {
        RectTreeNode node = root;
        while (!node.getChilds().isEmpty()) {
            node = node.getChilds().stream()
                    .filter(e -> e.getLeftTopLat() >= lat && e.getLeftTopLon() >= lon && e.getRightBotLat() <= lat && e.getRightBotLat() <= lon)
                    .findAny().orElseThrow();
        }
        return node;
    }

}
