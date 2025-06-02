package com.github.elephant.filesystem.dto;

public record ResourceResponse(
        Long id,
        String originalName,
        String extension,
        Long size,
        String mimeType,
        ResourcePresignedUrlResponse presignedUrl
) {
}
