package com.booking.perspective.media.model;

import com.booking.perspective.geo.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class MediaResponse {
    
    private final String payload;
    
    private final Coordinates coordinates;
    
}
