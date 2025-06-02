package com.github.elephant.filesystem.dto;

import java.time.LocalDateTime;

public record ResourcePresignedUrlResponse(
        String url,
        LocalDateTime expiresAt
) {
}
