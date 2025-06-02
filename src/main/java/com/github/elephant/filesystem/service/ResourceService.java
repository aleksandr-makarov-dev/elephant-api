package com.github.elephant.filesystem.service;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.entity.ResourceEntity;
import com.github.elephant.filesystem.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for managing resources in the filesystem.
 * Supports uploading files, retrieving presigned URLs, and fetching resource metadata.
 */
public interface ResourceService {

    /**
     * Uploads a file to the storage and saves its metadata.
     *
     * @param file the multipart file to upload
     * @return a response DTO containing resource metadata and presigned URL
     */
    ResourceResponse uploadResource(MultipartFile file);

    /**
     * Retrieves a presigned URL for accessing a resource by its ID.
     *
     * @param id the resource ID
     * @return a DTO containing the presigned URL and its expiry timestamp
     */
    ResourcePresignedUrlResponse getPresignedUrlById(Long id);

    /**
     * Fetches the resource entity by ID or throws if not found.
     *
     * @param id the resource ID
     * @return the ResourceEntity object
     * @throws ResourceNotFoundException if resource is not found
     */
    ResourceEntity getResourceEntityByIdOrThrow(Long id);
}
