package com.github.elephant.taskmanagement.dto;

import com.github.elephant.taskmanagement.entity.TaskPriority;
import com.github.elephant.taskmanagement.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public record TaskDetailsResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime createdAt,
        LocalDateTime dueDate,
        List<AttachmentResponse> attachments
) {
}
