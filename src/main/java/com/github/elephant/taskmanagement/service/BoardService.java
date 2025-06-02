package com.github.elephant.taskmanagement.service;

import com.github.elephant.taskmanagement.dto.BoardCreateRequest;
import com.github.elephant.taskmanagement.dto.BoardResponse;
import com.github.elephant.taskmanagement.dto.BoardUpdateRequest;
import com.github.elephant.taskmanagement.entity.BoardEntity;
import com.github.elephant.taskmanagement.exception.BoardNotFoundException;

import java.util.List;

/**
 * Service interface for handling operations related to boards.
 */
public interface BoardService {

    /**
     * Creates a new board with default status DRAFT.
     *
     * @param request the board creation request DTO
     * @return the created board wrapped in a BoardResponse DTO
     */
    BoardResponse createBoard(BoardCreateRequest request);

    /**
     * Retrieves all boards from the database.
     *
     * @return a list of BoardResponse DTOs
     */
    List<BoardResponse> getAllBoards();

    /**
     * Updates an existing board by its ID.
     *
     * @param id      the ID of the board to update
     * @param request the update request DTO
     * @return the updated board wrapped in a BoardResponse DTO
     */
    BoardResponse updateBoardById(Long id, BoardUpdateRequest request);

    /**
     * Deletes a board by its ID.
     *
     * @param id the ID of the board to delete
     */
    void deleteBoardById(Long id);

    /**
     * Helper method to retrieve a board by ID or throw a BoardNotFoundException if not found.
     *
     * @param id the ID of the board
     * @return the BoardEntity if found
     * @throws BoardNotFoundException if the board does not exist
     */
    BoardEntity getBoardEntityByIdOrThrow(Long id);
}
