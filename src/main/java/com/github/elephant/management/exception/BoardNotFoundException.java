package com.github.elephant.management.exception;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(Long id) {
        super("Board with id = %s not found".formatted(id));
    }
}
