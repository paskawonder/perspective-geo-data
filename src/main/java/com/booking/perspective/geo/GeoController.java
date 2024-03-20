package com.booking.perspective.geo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geo")
public class GeoController {
    
    private final GeoService geoService;
    
    @Autowired
    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }
    
    @GetMapping
    public List<List<List<String>>> get() {
        return geoService.get().stream().map(e -> {
            List<List<String>> list = new ArrayList<>();
            list.add(List.of(e.getLeftTopLat().toString(), e.getLeftTopLon().toString()));
            list.add(List.of(e.getLeftTopLat().toString(), e.getRightBotLon().toString()));
            list.add(List.of(e.getRightBotLat().toString(), e.getRightBotLon().toString()));
            list.add(List.of(e.getRightBotLat().toString(), e.getLeftTopLon().toString()));
            return list;
        }).toList();
    }
    
    @PostMapping
    public void split() {
        geoService.defaultSplit();
    }
    
}
