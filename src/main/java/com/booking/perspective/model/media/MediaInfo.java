package com.booking.perspective.model.media;

import java.time.LocalDateTime;

public record MediaInfo(String fileId, String userId,
                        double lat, double lon, LocalDateTime utcDateTime) {
}
