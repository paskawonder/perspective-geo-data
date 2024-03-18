package com.booking.perspective.api.rest.model;

import java.time.LocalDateTime;

public record MediaRequest(byte[] payload, String userId,
                           Double lat, Double lon, LocalDateTime utcDateTime) {
}
