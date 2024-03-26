package com.booking.perspective.geo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class Coordinates {
    
    private final BigDecimal lat;
    
    private final BigDecimal lng;
    
    @JsonCreator
    public Coordinates(@JsonProperty("lat") String lat, @JsonProperty("lng") String lng) {
        BigDecimal temp = new BigDecimal(lat);
        while (temp.compareTo(BigDecimal.valueOf(90)) > 0) {
            temp = temp.subtract(BigDecimal.valueOf(180));
        }
        while (temp.compareTo(BigDecimal.valueOf(-90)) < 0) {
            temp = temp.add(BigDecimal.valueOf(180));
        }
        this.lat = new BigDecimal(temp.unscaledValue(), temp.scale());
        temp = new BigDecimal(lng);
        while (temp.compareTo(BigDecimal.valueOf(180)) > 0) {
            temp = temp.subtract(BigDecimal.valueOf(360));
        }
        while (temp.compareTo(BigDecimal.valueOf(-180)) < 0) {
            temp = temp.add(BigDecimal.valueOf(360));
        }
        this.lng = new BigDecimal(temp.unscaledValue(), temp.scale());
    }
    
}
