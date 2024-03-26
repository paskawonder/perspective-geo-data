package com.booking.perspective.media;

import com.booking.perspective.geo.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class MediaDTO {
    
    private String userId;
    
    private String payload;
    
    private Coordinates coordinates;
    
}
