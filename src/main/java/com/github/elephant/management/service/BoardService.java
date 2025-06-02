package com.github.elephant.management.service;

import com.github.elephant.management.dto.BoardCreateRequest;
import com.github.elephant.management.dto.BoardResponse;
import com.github.elephant.management.dto.BoardUpdateRequest;

import java.util.List;


public interface BoardService {

    BoardResponse createBoard(BoardCreateRequest request);

    List<BoardResponse> getAllBoards();

    BoardResponse updateBoardById(Long id, BoardUpdateRequest request);

    void deleteBoardById(Long id);
}
