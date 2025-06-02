package com.github.elephant.taskmanagement.controller;

import com.github.elephant.taskmanagement.dto.TaskCreateRequest;
import com.github.elephant.taskmanagement.dto.TaskDetailsResponse;
import com.github.elephant.taskmanagement.dto.TaskSummaryResponse;
import com.github.elephant.taskmanagement.dto.TaskUpdateRequest;
import com.github.elephant.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Task resources.
 */
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Endpoints for managing tasks within boards")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create a new task", description = "Creates a task under a specified board")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid task data")
    })
    @PostMapping
    public ResponseEntity<TaskSummaryResponse> createTask(
            @RequestBody @Valid TaskCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @Operation(summary = "Get all tasks for a board", description = "Retrieves a list of tasks for a specific board")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid board ID")
    })
    @GetMapping
    public ResponseEntity<List<TaskSummaryResponse>> getAllTasksByBoardId(
            @Parameter(description = "ID of the board") @RequestParam(value = "boardId") Long boardId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasksByBoardId(boardId));
    }

    @Operation(summary = "Get a task by ID", description = "Retrieves detailed information about a specific task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<TaskDetailsResponse> getTaskById(
            @Parameter(description = "ID of the task") @PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(id));
    }

    @Operation(summary = "Update a task", description = "Updates a task's details by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<TaskSummaryResponse> updateTaskById(
            @Parameter(description = "ID of the task to update") @PathVariable("id") Long id,
            @RequestBody @Valid TaskUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskById(id, request));
    }

    @Operation(summary = "Delete a task", description = "Deletes a task by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTaskById(
            @Parameter(description = "ID of the task to delete") @PathVariable("id") Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
