package com.github.elephant.management.service;

import com.github.elephant.management.dto.TaskCreateRequest;
import com.github.elephant.management.dto.TaskResponse;
import com.github.elephant.management.dto.TaskUpdateRequest;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskCreateRequest request);

    List<TaskResponse> getAllTasksByBoardId(Long boardId);

    TaskResponse updateTaskById(Long id, TaskUpdateRequest request);

    void deleteTaskById(Long id);
}
