package com.github.elephant.management.service;

import com.github.elephant.management.dto.BoardCreateRequest;
import com.github.elephant.management.dto.BoardResponse;

import java.util.List;

public interface BoardService {

    BoardResponse createBoard(BoardCreateRequest request);

    List<BoardResponse> getAllBoards();

    void deleteBoardById(Long id);
}
