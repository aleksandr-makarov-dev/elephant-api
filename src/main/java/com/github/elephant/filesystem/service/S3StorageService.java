package com.github.elephant.filesystem.service;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;

import java.io.InputStream;

/**
 * Service interface for interacting with S3-compatible object storage.
 * Provides methods to upload objects and generate presigned URLs for access.
 */
public interface S3StorageService {

    /**
     * Uploads an object to the configured bucket.
     *
     * @param key          the key (path/filename) under which the object will be stored
     * @param mimeType     the MIME type of the object being uploaded
     * @param size         the size of the input stream content in bytes
     * @param inputStream  the input stream containing the object's data
     */
    void putObject(String key, String mimeType, Long size, InputStream inputStream);

    /**
     * Generates a presigned URL to access the specified object for a limited time.
     *
     * @param key         the key of the object in the bucket
     * @param expireTime  the expiration time in seconds for the presigned URL
     * @return a response containing the presigned URL and its expiry timestamp
     */
    ResourcePresignedUrlResponse getPresignedUrl(String key, Integer expireTime);
}
