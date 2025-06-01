package com.github.elephant.management.mapper;

import com.github.elephant.management.dto.TaskCreateRequest;
import com.github.elephant.management.dto.TaskResponse;
import com.github.elephant.management.dto.TaskUpdateRequest;
import com.github.elephant.management.entity.TaskEntity;
import org.springframework.stereotype.Component;

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

    public TaskResponse toTaskResponse(TaskEntity entity) {
        return new TaskResponse(
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

}
