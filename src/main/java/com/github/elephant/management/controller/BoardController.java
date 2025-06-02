package com.github.elephant.management.controller;

import com.github.elephant.management.dto.BoardCreateRequest;
import com.github.elephant.management.dto.BoardResponse;
import com.github.elephant.management.dto.BoardUpdateRequest;
import com.github.elephant.management.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody @Valid BoardCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(request));
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getAllBoards());
    }

    @PutMapping("{id}")
    public ResponseEntity<BoardResponse> updateBoardById(@PathVariable("id") Long id, @RequestBody @Valid BoardUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoardById(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBoardById(@PathVariable("id") Long id) {
        boardService.deleteBoardById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
