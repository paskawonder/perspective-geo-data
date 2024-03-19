package com.booking.perspective.api.rest;

import com.booking.perspective.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final LocationService locationService;
    
    @Autowired
    public AdminController(LocationService locationService) {
        this.locationService = locationService;
    }
    
    @GetMapping
    public void split() {
        locationService.defaultSplit();
    }
    
}
