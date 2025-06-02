package com.github.elephant.taskmanagement.dto;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;

public record AttachmentResponse(
        Long id,
        String name,
        String extension,
        Long size,
        ResourcePresignedUrlResponse presignedUrl
) {
}
