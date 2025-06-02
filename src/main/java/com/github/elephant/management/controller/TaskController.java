package com.github.elephant.management.controller;

import com.github.elephant.management.dto.TaskCreateRequest;
import com.github.elephant.management.dto.TaskDetailsResponse;
import com.github.elephant.management.dto.TaskSummaryResponse;
import com.github.elephant.management.dto.TaskUpdateRequest;
import com.github.elephant.management.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskSummaryResponse> createTask(@RequestBody @Valid TaskCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @GetMapping
    public ResponseEntity<List<TaskSummaryResponse>> getAllTasksByBoardId(@RequestParam(value = "boardId") Long boardId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasksByBoardId(boardId));
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDetailsResponse> getTaskById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskSummaryResponse> updateTaskById(@PathVariable("id") Long id, @RequestBody @Valid TaskUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskById(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("id") Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
