package com.github.elephant.filesystem.dto;

import java.time.LocalDateTime;

public record ResourceResponse(
        Long id,
        String originalName,
        String extension,
        Long size,
        String mimeType,
        String downloadUrl,
        LocalDateTime expiresAt
) {
}
