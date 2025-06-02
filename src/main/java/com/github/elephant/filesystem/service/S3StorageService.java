package com.github.elephant.filesystem.service;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;

import java.io.InputStream;

public interface S3StorageService {
    void putObject(String key, String mimeType, Long size, InputStream inputStream);

    ResourcePresignedUrlResponse getPresignedUrl(String key, Integer expireTime);
}
