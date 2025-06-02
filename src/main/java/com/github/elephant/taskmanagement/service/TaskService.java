package com.github.elephant.taskmanagement.service;

import com.github.elephant.taskmanagement.dto.TaskCreateRequest;
import com.github.elephant.taskmanagement.dto.TaskDetailsResponse;
import com.github.elephant.taskmanagement.dto.TaskSummaryResponse;
import com.github.elephant.taskmanagement.dto.TaskUpdateRequest;

import java.util.List;

/**
 * Service interface for handling operations related to tasks.
 */
public interface TaskService {

    /**
     * Creates a new task and associates it with a board.
     *
     * @param request the task creation request payload
     * @return summary response of the created task
     */
    TaskSummaryResponse createTask(TaskCreateRequest request);

    /**
     * Retrieves all tasks belonging to a specific board.
     *
     * @param boardId the ID of the board
     * @return list of task summary responses
     */
    List<TaskSummaryResponse> getAllTasksByBoardId(Long boardId);

    /**
     * Retrieves detailed information about a specific task by its ID.
     *
     * @param id the ID of the task
     * @return detailed task response including attachments
     */
    TaskDetailsResponse getTaskById(Long id);

    /**
     * Updates a task with the given ID using the provided update request.
     *
     * @param id      the ID of the task to update
     * @param request the task update request payload
     * @return summary response of the updated task
     */
    TaskSummaryResponse updateTaskById(Long id, TaskUpdateRequest request);

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     */
    void deleteTaskById(Long id);
}
