package com.github.elephant.taskmanagement.service;

import com.github.elephant.taskmanagement.dto.BoardCreateRequest;
import com.github.elephant.taskmanagement.dto.BoardResponse;
import com.github.elephant.taskmanagement.dto.BoardUpdateRequest;
import com.github.elephant.taskmanagement.entity.BoardEntity;
import com.github.elephant.taskmanagement.entity.BoardStatus;
import com.github.elephant.taskmanagement.exception.BoardNotFoundException;
import com.github.elephant.taskmanagement.mapper.BoardMapper;
import com.github.elephant.taskmanagement.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    @Transactional
    @Override
    public BoardResponse createBoard(BoardCreateRequest request) {
        log.info("Creating board with title = {}", request.title());

        BoardEntity board = boardMapper.toBoardEntity(request);
        board.setStatus(BoardStatus.DRAFT);
        return boardMapper.toBoardResponse(boardRepository.save(board));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardResponse> getAllBoards() {
        log.info("Getting all boards");

        return boardRepository.findAll()
                .stream()
                .map(boardMapper::toBoardResponse)
                .toList();
    }

    @Transactional
    @Override
    public BoardResponse updateBoardById(Long id, BoardUpdateRequest request) {
        log.info("Updating board with id = {}", id);

        BoardEntity board = getBoardEntityByIdOrThrow(id);
        BoardEntity updatedBoard = boardMapper.updateBoardEntity(board, request);

        return boardMapper.toBoardResponse(boardRepository.save(updatedBoard));
    }

    @Transactional
    @Override
    public void deleteBoardById(Long id) {
        log.info("Deleting board with id = {}", id);

        BoardEntity board = getBoardEntityByIdOrThrow(id);

        boardRepository.delete(board);
    }

    @Override
    public BoardEntity getBoardEntityByIdOrThrow(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id));
    }
}
