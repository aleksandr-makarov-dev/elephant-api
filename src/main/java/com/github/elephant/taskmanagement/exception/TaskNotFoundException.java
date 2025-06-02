package com.github.elephant.taskmanagement.exception;



public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task with id = %s not found".formatted(id));
    }
}
