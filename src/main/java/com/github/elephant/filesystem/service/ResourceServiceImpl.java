package com.github.elephant.filesystem.service;

import com.github.elephant.filesystem.dto.ResourceDownloadResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.dto.ResourceUploadResponse;
import com.github.elephant.filesystem.entity.ResourceEntity;
import com.github.elephant.filesystem.exception.InputStreamException;
import com.github.elephant.filesystem.exception.ResourceNotFoundException;
import com.github.elephant.filesystem.exception.S3StorageException;
import com.github.elephant.filesystem.mapper.ResourceMapper;
import com.github.elephant.filesystem.repository.ResourceRepository;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private static final String TMP_DIR = "tmp";
    private static final Duration EXPIRE_TIME = Duration.ofHours(1);

    private final S3StorageService s3StorageService;

    private final ResourceRepository resourceRepository;

    private final ResourceMapper resourceMapper;

    @Override
    public ResourceUploadResponse uploadResource(MultipartFile file) {


        UUID uuid = UUID.randomUUID();
        String originalFilename = FilenameUtils.getName(file.getOriginalFilename());
        String mimeType = file.getContentType();
        String extension = FilenameUtils.getExtension(originalFilename);
        long fileSize = file.getSize();
        String key = String.join("/", TMP_DIR, String.join(".", uuid.toString(), extension));

        log.info("Uploading file '{}', size={} bytes", originalFilename, fileSize);

        try {
            // save resource to storage
            s3StorageService.putObject(key, mimeType, fileSize, file.getInputStream());

            // save resource metadata to database
            ResourceEntity resourceEntity = ResourceEntity.builder()
                    .id(uuid)
                    .name(originalFilename)
                    .mimeType(mimeType)
                    .extension(extension)
                    .key(key)
                    .size(fileSize)
                    .build();

            return resourceMapper.toResourceUploadResponse(resourceRepository.save(resourceEntity));
        } catch (IOException e) {
            throw new InputStreamException(e);
        }
    }

    @Override
    public ResourceDownloadResponse downloadResourceById(UUID id) {

        log.info("Downloading file with id = {}", id);

        ResourceEntity resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with id = %s not found".formatted(id)));

        String presignedUrl = s3StorageService.getPresignedUrl(resource.getKey(), (int) EXPIRE_TIME.getSeconds());

        return new ResourceDownloadResponse(presignedUrl, LocalDateTime.now().plusSeconds(EXPIRE_TIME.getSeconds()));
    }

    @Override
    public List<ResourceResponse> getAllResources() {
        return resourceRepository.findAll()
                .stream()
                .map(resourceMapper::toResourceResponse)
                .collect(Collectors.toList());
    }
}
