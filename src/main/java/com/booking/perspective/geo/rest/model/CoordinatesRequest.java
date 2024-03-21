package com.booking.perspective.geo.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class CoordinatesRequest {
    
    private final BigDecimal lat;
    
    private final BigDecimal lon;
    
    @JsonCreator
    public CoordinatesRequest(@JsonProperty("lat") String lat, @JsonProperty("lon") String lon) {
        BigDecimal temp = new BigDecimal(lat);
        while (temp.compareTo(BigDecimal.valueOf(90)) > 0) {
            temp = temp.subtract(BigDecimal.valueOf(180));
        }
        while (temp.compareTo(BigDecimal.valueOf(-90)) < 0) {
            temp = temp.add(BigDecimal.valueOf(180));
        }
        this.lat = new BigDecimal(temp.unscaledValue(), temp.scale());
        temp = new BigDecimal(lon);
        while (temp.compareTo(BigDecimal.valueOf(180)) > 0) {
            temp = temp.subtract(BigDecimal.valueOf(360));
        }
        while (temp.compareTo(BigDecimal.valueOf(-180)) < 0) {
            temp = temp.add(BigDecimal.valueOf(360));
        }
        this.lon = new BigDecimal(temp.unscaledValue(), temp.scale());
    }
    
}
