package com.github.elephant.filesystem.service;

import java.io.InputStream;

public interface S3StorageService {
    void putObject(String key, String mimeType, Long size, InputStream inputStream);

    String getPresignedUrl(String key, Integer expireTime);
}
