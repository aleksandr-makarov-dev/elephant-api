package com.github.elephant.management.dto;

import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.management.entity.TaskPriority;
import com.github.elephant.management.entity.TaskStatus;

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
        List<ResourceResponse> attachments
){
}
