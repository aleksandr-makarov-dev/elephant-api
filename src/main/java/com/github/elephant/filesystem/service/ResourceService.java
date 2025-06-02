package com.github.elephant.filesystem.service;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.entity.ResourceEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {

    ResourceResponse uploadResource(MultipartFile file);

    ResourcePresignedUrlResponse getPresignedUrlById(Long id);

    ResourceEntity getResourceEntityByIdOrThrow(Long id);
}
