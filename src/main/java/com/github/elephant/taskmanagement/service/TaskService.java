package com.github.elephant.taskmanagement.service;

import com.github.elephant.taskmanagement.dto.TaskCreateRequest;
import com.github.elephant.taskmanagement.dto.TaskDetailsResponse;
import com.github.elephant.taskmanagement.dto.TaskSummaryResponse;
import com.github.elephant.taskmanagement.dto.TaskUpdateRequest;

import java.util.List;


public interface TaskService {

    TaskSummaryResponse createTask(TaskCreateRequest request);

    List<TaskSummaryResponse> getAllTasksByBoardId(Long boardId);

    TaskDetailsResponse getTaskById(Long id);

    TaskSummaryResponse updateTaskById(Long id, TaskUpdateRequest request);

    void deleteTaskById(Long id);
}
