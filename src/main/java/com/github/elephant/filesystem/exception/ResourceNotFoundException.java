package com.github.elephant.filesystem.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super(
                String.format("Resource with id = %d not found", id)
        );
    }
}
