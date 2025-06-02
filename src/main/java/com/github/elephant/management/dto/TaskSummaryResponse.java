package com.github.elephant.management.dto;

import com.github.elephant.management.entity.TaskPriority;
import com.github.elephant.management.entity.TaskStatus;

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
