package com.github.elephant.management.service;

import com.github.elephant.management.dto.TaskCreateRequest;
import com.github.elephant.management.dto.TaskDetailsResponse;
import com.github.elephant.management.dto.TaskSummaryResponse;
import com.github.elephant.management.dto.TaskUpdateRequest;

import java.util.List;


public interface TaskService {

    TaskSummaryResponse createTask(TaskCreateRequest request);

    List<TaskSummaryResponse> getAllTasksByBoardId(Long boardId);

    TaskDetailsResponse getTaskById(Long id);

    TaskSummaryResponse updateTaskById(Long id, TaskUpdateRequest request);

    void deleteTaskById(Long id);
}
