package com.github.elephant.filesystem.service;

import com.github.elephant.filesystem.exception.S3StorageException;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class S3StorageServiceImpl implements S3StorageService {

    private static final int PRESIGNED_URL_EXPIRY = 3600;

    private final MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    private String bucket;

    @PostConstruct
    public void init() throws Exception {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
                .bucket(bucket)
                .build();

        boolean bucketExists = minioClient.bucketExists(bucketExistsArgs);

        if (!bucketExists) {
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build();

            minioClient.makeBucket(makeBucketArgs);
        }
    }

    public void putObject(String key, String mimeType, Long size, InputStream inputStream) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .contentType(mimeType)
                    .stream(inputStream, size, -1)
                    .build();

            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new S3StorageException(e);
        }
    }

    public String getPresignedUrl(String key, Integer expireTime) {
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(bucket)
                .object(key)
                .expiry(expireTime, TimeUnit.SECONDS)
                .method(Method.GET)
                .build();

        try {
            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
