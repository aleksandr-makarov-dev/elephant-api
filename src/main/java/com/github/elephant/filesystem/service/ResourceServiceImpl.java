package com.github.elephant.filesystem.service;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.entity.ResourceEntity;
import com.github.elephant.filesystem.exception.InputStreamException;
import com.github.elephant.filesystem.exception.ResourceNotFoundException;
import com.github.elephant.filesystem.mapper.ResourceMapper;
import com.github.elephant.filesystem.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    @Value("${spring.minio.expiry}")
    private int EXPIRE_TIME;

    private static final String TMP_DIR = "tmp";

    private final S3StorageService s3StorageService;

    private final ResourceRepository resourceRepository;

    private final ResourceMapper resourceMapper;

    @Transactional
    @Override
    public ResourceResponse uploadResource(MultipartFile file) {


        String originalFilename = FilenameUtils.getName(file.getOriginalFilename());
        String safeFilename = UUID.randomUUID().toString();
        String mimeType = file.getContentType();
        String extension = FilenameUtils.getExtension(originalFilename);
        long fileSize = file.getSize();
        String key = String.join("/", TMP_DIR, String.join(".", safeFilename, extension));

        log.info("Uploading file '{}', size={} bytes", originalFilename, fileSize);

        try {
            // save resource to storage
            s3StorageService.putObject(key, mimeType, fileSize, file.getInputStream());

            // save resource metadata to database
            ResourceEntity resourceEntity = ResourceEntity.builder()
                    .name(originalFilename)
                    .mimeType(mimeType)
                    .extension(extension)
                    .key(key)
                    .size(fileSize)
                    .build();

            ResourcePresignedUrlResponse presignedUrl = s3StorageService.getPresignedUrl(resourceEntity.getKey(), EXPIRE_TIME);

            return resourceMapper.toResourceResponse(
                    resourceRepository.save(resourceEntity),
                    presignedUrl
            );
        } catch (IOException e) {
            throw new InputStreamException(e);
        }
    }

    @Transactional
    @Override
    public ResourcePresignedUrlResponse getPresignedUrlById(Long id) {

        log.info("Downloading file with id = {}", id);

        ResourceEntity resource = getResourceEntityByIdOrThrow(id);

        return s3StorageService.getPresignedUrl(resource.getKey(), EXPIRE_TIME);
    }

    @Override
    public ResourceEntity getResourceEntityByIdOrThrow(Long id) {
        return resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
