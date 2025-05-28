package com.github.elephant.management.controller;

import com.github.elephant.management.dto.TaskCreateRequest;
import com.github.elephant.management.dto.TaskResponse;
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
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskCreateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.createTask(request));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasksByBoardId(@RequestParam(value = "boardId") Long boardId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasksByBoardId(boardId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("id") Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
