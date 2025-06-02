package com.github.elephant.taskmanagement.service;

import com.github.elephant.taskmanagement.dto.*;
import com.github.elephant.taskmanagement.entity.BoardEntity;
import com.github.elephant.taskmanagement.entity.TaskEntity;
import com.github.elephant.taskmanagement.entity.TaskStatus;
import com.github.elephant.taskmanagement.exception.TaskNotFoundException;
import com.github.elephant.taskmanagement.mapper.TaskMapper;
import com.github.elephant.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final BoardService boardService;
    private final AttachmentService attachmentService;

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Transactional
    @Override
    public TaskSummaryResponse createTask(TaskCreateRequest request) {
        log.info("Creating task with title = {} and boardId = {}", request.title(), request.boardId());

        BoardEntity board = boardService.getBoardEntityByIdOrThrow(request.boardId());

        TaskEntity task = taskMapper.toTaskEntity(request);
        task.setStatus(TaskStatus.PLANNED);
        task.setBoard(board);

        TaskEntity savedTask = taskRepository.save(task);

        if (request.attachments() != null && !request.attachments().isEmpty()) {
            attachmentService.saveAllAttachments(savedTask, request.attachments());
        }

        return taskMapper.toTaskSummaryResponse(task);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskSummaryResponse> getAllTasksByBoardId(Long boardId) {
        log.info("Getting all tasks for boardId = {}", boardId);

        BoardEntity board = boardService.getBoardEntityByIdOrThrow(boardId);

        return taskRepository.findAllByBoardId(board.getId())
                .stream()
                .map(taskMapper::toTaskSummaryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDetailsResponse getTaskById(Long id) {
        TaskEntity task = getTaskEntityByIdOrThrow(id);

        List<AttachmentResponse> attachments = attachmentService.getAttachmentsByTaskId(task.getId());

        return taskMapper.toTaskDetailsResponse(task, attachments);
    }

    @Transactional
    @Override
    public TaskSummaryResponse updateTaskById(Long id, TaskUpdateRequest request) {
        log.info("Updating task with id = {}", id);

        TaskEntity task = getTaskEntityByIdOrThrow(id);
        TaskEntity updatedTask = taskMapper.updateTaskEntity(task, request);

        return taskMapper.toTaskSummaryResponse(taskRepository.save(updatedTask));
    }

    @Transactional
    @Override
    public void deleteTaskById(Long id) {
        log.info("Deleting task with id = {}", id);

        TaskEntity task = getTaskEntityByIdOrThrow(id);
        taskRepository.delete(task);
    }

    private TaskEntity getTaskEntityByIdOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
