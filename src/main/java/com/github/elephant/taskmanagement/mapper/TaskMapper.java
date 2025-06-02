package com.github.elephant.taskmanagement.mapper;

import com.github.elephant.taskmanagement.dto.*;
import com.github.elephant.taskmanagement.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {

    public TaskEntity toTaskEntity(TaskCreateRequest request) {
        return TaskEntity.builder()
                .title(request.title())
                .description(request.description())
                .priority(request.priority())
                .dueDate(request.dueDate())
                .build();
    }

    public TaskSummaryResponse toTaskSummaryResponse(TaskEntity entity) {
        return new TaskSummaryResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getPriority(),
                entity.getCreatedAt(),
                entity.getDueDate()
        );
    }

    public TaskEntity updateTaskEntity(TaskEntity entity, TaskUpdateRequest request) {
        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setStatus(request.status());
        entity.setPriority(request.priority());
        entity.setDueDate(request.dueDate());

        return entity;
    }

    public TaskDetailsResponse toTaskDetailsResponse(TaskEntity entity, List<AttachmentResponse> attachments) {
        return new TaskDetailsResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getPriority(),
                entity.getCreatedAt(),
                entity.getDueDate(),
                attachments
        );
    }
}
