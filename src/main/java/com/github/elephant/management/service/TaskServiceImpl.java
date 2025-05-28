package com.github.elephant.management.service;

import com.github.elephant.management.dto.TaskCreateRequest;
import com.github.elephant.management.dto.TaskResponse;
import com.github.elephant.management.entity.BoardEntity;
import com.github.elephant.management.entity.TaskEntity;
import com.github.elephant.management.entity.TaskStatus;
import com.github.elephant.management.exception.BoardNotFoundException;
import com.github.elephant.management.exception.TaskNotFoundException;
import com.github.elephant.management.mapper.TaskMapper;
import com.github.elephant.management.repository.BoardRepository;
import com.github.elephant.management.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final BoardRepository boardRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    @Override
    public TaskResponse createTask(TaskCreateRequest request) {
        log.info("Creating task with title = {} and boardId = {}", request.title(), request.boardId());

        BoardEntity board = getBoardByIdOrThrow(request.boardId());

        TaskEntity task = taskMapper.toTaskEntity(request);
        task.setStatus(TaskStatus.PLANNED);
        task.setBoard(board);

        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskResponse> getAllTasksByBoardId(Long boardId) {
        log.info("Getting all tasks for boardId = {}", boardId);

        BoardEntity board = getBoardByIdOrThrow(boardId);

        return taskRepository.findAllByBoardId(board.getId())
                .stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    @Transactional
    @Override
    public void deleteTaskById(Long id) {
        log.info("Deleting task with id = {}", id);

        TaskEntity task = getTaskByIdOrThrow(id);
        taskRepository.delete(task);
    }

    private TaskEntity getTaskByIdOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private BoardEntity getBoardByIdOrThrow(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId));
    }
}
