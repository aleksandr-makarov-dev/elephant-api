package com.github.elephant.filesystem.mapper;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.entity.ResourceEntity;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

    public ResourceResponse toResourceResponse(ResourceEntity entity, ResourcePresignedUrlResponse presignedUrl) {
        return new ResourceResponse(
                entity.getId(),
                entity.getName(),
                entity.getExtension(),
                entity.getSize(),
                entity.getMimeType(),
                presignedUrl
        );
    }
}
