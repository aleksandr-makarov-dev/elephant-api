package com.github.elephant.filesystem.mapper;

import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.dto.ResourceUploadResponse;
import com.github.elephant.filesystem.entity.ResourceEntity;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

    public ResourceUploadResponse toResourceUploadResponse(ResourceEntity entity) {
        return new ResourceUploadResponse(
                entity.getId(),
                entity.getName(),
                entity.getExtension(),
                entity.getSize(),
                entity.getMimeType()
        );
    }

    public ResourceResponse toResourceResponse(ResourceEntity entity) {
        return new ResourceResponse(
                entity.getId(),
                entity.getName(),
                entity.getExtension(),
                entity.getSize(),
                entity.getMimeType()
        );
    }
}
