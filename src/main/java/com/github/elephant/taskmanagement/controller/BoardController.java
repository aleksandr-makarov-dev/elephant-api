package com.github.elephant.taskmanagement.controller;

import com.github.elephant.taskmanagement.dto.BoardCreateRequest;
import com.github.elephant.taskmanagement.dto.BoardResponse;
import com.github.elephant.taskmanagement.dto.BoardUpdateRequest;
import com.github.elephant.taskmanagement.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Board resources.
 * Handles HTTP requests for creating, retrieving, updating, and deleting boards.
 */
@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@Tag(name = "Boards", description = "Endpoints for managing boards")
public class BoardController {

    /** Service layer dependency for handling board business logic */
    private final BoardService boardService;

    @Operation(summary = "Create a new board", description = "Creates and returns a new board")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Board created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(
            @RequestBody @Valid BoardCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(request));
    }

    @Operation(summary = "Get all boards", description = "Retrieves all existing boards")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Boards retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getAllBoards());
    }

    @Operation(summary = "Update a board by ID", description = "Updates the details of a board by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Board updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update request"),
            @ApiResponse(responseCode = "404", description = "Board not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<BoardResponse> updateBoardById(
            @Parameter(description = "ID of the board to update") @PathVariable("id") Long id,
            @RequestBody @Valid BoardUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoardById(id, request));
    }

    @Operation(summary = "Delete a board by ID", description = "Deletes a board by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Board deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Board not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBoardById(
            @Parameter(description = "ID of the board to delete") @PathVariable("id") Long id) {
        boardService.deleteBoardById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
