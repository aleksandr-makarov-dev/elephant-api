package com.github.elephant.filesystem.exception;

public class S3StorageException extends RuntimeException {
    public S3StorageException(String message) {
        super(message);
    }

    public S3StorageException(Throwable cause) {
        super(cause);
    }
}
