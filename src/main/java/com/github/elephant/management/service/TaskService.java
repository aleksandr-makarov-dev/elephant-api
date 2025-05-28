package com.github.elephant.management.service;

import com.github.elephant.management.dto.TaskCreateRequest;
import com.github.elephant.management.dto.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskCreateRequest request);

    List<TaskResponse> getAllTasksByBoardId(Long boardId);

    void deleteTaskById(Long id);
}
