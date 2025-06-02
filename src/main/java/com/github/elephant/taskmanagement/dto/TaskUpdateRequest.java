package com.github.elephant.taskmanagement.dto;

import com.github.elephant.taskmanagement.entity.TaskPriority;
import com.github.elephant.taskmanagement.entity.TaskStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record TaskUpdateRequest(
        @NotBlank(message = "Title must not be empty")
        @Size(min = 5, max = 100, message = "Title length must be between {min} and {max} characters")
        String title,

        @Size(min = 5, max = 500, message = "Description length must be between {min} and {max} characters")
        String description,

        @NotNull(message = "Status must be specified")
        TaskStatus status,

        @NotNull(message = "Priority must be specified")
        TaskPriority priority,

        @Future(message = "Due date must be in the future")
        LocalDateTime dueDate
        ) {
}
