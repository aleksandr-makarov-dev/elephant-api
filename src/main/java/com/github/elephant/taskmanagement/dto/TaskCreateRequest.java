package com.github.elephant.taskmanagement.dto;

import com.github.elephant.taskmanagement.entity.TaskPriority;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;


public record TaskCreateRequest(
        @NotBlank(message = "Title must not be empty")
        @Size(min = 5, max = 100, message = "Title length must be between {min} and {max} characters")
        String title,

        @Size(min = 5, max = 500, message = "Description length must be between {min} and {max} characters")
        String description,

        @NotNull(message = "Priority must be specified")
        TaskPriority priority,

        @Future(message = "Due date must be in the future")
        LocalDateTime dueDate,

        @NotNull(message = "Board Id must be provided")
        Long boardId,

        List<Long> attachments
) {
}
