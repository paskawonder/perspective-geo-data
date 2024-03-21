package com.booking.perspective.geo.rest;

import com.booking.perspective.geo.GeoService;
import com.booking.perspective.geo.entity.QuadTreeNode;
import com.booking.perspective.geo.rest.model.CoordinatesRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @PostMapping
    public void split(@RequestBody CoordinatesRequest request) {
        geoService.split(request.getLat(), request.getLon());
    }
    
    @GetMapping
    public List<List<List<String>>> get() {
        return geoService.getLeaves().stream().map(this::mapRectToLines).toList();
    }
    
    @PostMapping("/adjs")
    public List<List<List<String>>> get(@RequestBody CoordinatesRequest request) {
        return geoService.adjs(request.getLat(), request.getLon()).stream().map(this::mapRectToLines).toList();
    }
    
    private List<List<String>> mapRectToLines(QuadTreeNode e) {
        return List.of(
                List.of(e.getLeftTopLat().toString(), e.getLeftTopLon().toString()),
                List.of(e.getLeftTopLat().toString(), e.getRightBotLon().toString()),
                List.of(e.getRightBotLat().toString(), e.getRightBotLon().toString()),
                List.of(e.getRightBotLat().toString(), e.getLeftTopLon().toString())
        );
    }
    
}
