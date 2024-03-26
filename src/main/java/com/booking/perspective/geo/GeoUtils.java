package com.booking.perspective.geo;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class GeoUtils {
    
    private static final int R = 6371;
    
    public double dist(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2, BigDecimal lng2) {
        return dist(lat1.doubleValue(), lng1.doubleValue(), lat2.doubleValue(), lng2.doubleValue());
    }
    
    public double dist(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians((lat2 - lat1));
        double dLng = Math.toRadians((lng2 - lng1));
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = haversine(dLat) + Math.cos(lat1) * Math.cos(lat2) * haversine(dLng);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    
    private double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
    
}
