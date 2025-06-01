package com.github.elephant.filesystem.dto;

import java.time.LocalDateTime;

public record ResourceDownloadResponse(
        String downloadUrl,
        LocalDateTime expiresAt
) {
}
