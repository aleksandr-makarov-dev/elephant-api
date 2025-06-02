package com.github.elephant.taskmanagement.dto;

import com.github.elephant.taskmanagement.entity.TaskPriority;
import com.github.elephant.taskmanagement.entity.TaskStatus;

import java.time.LocalDateTime;


public record TaskSummaryResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime createdAt,
        LocalDateTime dueDate
) {
}
