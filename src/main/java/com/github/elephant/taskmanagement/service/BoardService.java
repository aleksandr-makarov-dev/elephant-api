package com.github.elephant.taskmanagement.service;

import com.github.elephant.taskmanagement.dto.BoardCreateRequest;
import com.github.elephant.taskmanagement.dto.BoardResponse;
import com.github.elephant.taskmanagement.dto.BoardUpdateRequest;
import com.github.elephant.taskmanagement.entity.BoardEntity;

import java.util.List;


public interface BoardService {

    BoardResponse createBoard(BoardCreateRequest request);

    List<BoardResponse> getAllBoards();

    BoardResponse updateBoardById(Long id, BoardUpdateRequest request);

    void deleteBoardById(Long id);

    BoardEntity getBoardEntityByIdOrThrow(Long id);
}
