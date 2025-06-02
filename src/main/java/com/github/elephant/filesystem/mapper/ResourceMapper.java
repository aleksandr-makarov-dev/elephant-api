package com.github.elephant.filesystem.mapper;

import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.entity.ResourceEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResourceMapper {

    public ResourceResponse toResourceResponse(ResourceEntity entity, String downloadUrl, Integer expireTime) {
        return new ResourceResponse(
                entity.getId(),
                entity.getName(),
                entity.getExtension(),
                entity.getSize(),
                entity.getMimeType(),
                downloadUrl,
                LocalDateTime.now().plusSeconds(expireTime)
        );
    }
}
