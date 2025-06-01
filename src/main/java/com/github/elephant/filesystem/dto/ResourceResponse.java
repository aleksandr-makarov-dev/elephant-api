package com.github.elephant.filesystem.dto;

import java.util.UUID;

public record ResourceResponse(
        UUID id,
        String originalName,
        String extension,
        Long size,
        String mimeType
) {
}
