package com.github.elephant.management.service;

import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.entity.ResourceEntity;
import com.github.elephant.filesystem.exception.ResourceNotFoundException;
import com.github.elephant.filesystem.mapper.ResourceMapper;
import com.github.elephant.filesystem.repository.ResourceRepository;
import com.github.elephant.filesystem.service.S3StorageService;
import com.github.elephant.management.dto.TaskCreateRequest;
import com.github.elephant.management.dto.TaskDetailsResponse;
import com.github.elephant.management.dto.TaskSummaryResponse;
import com.github.elephant.management.dto.TaskUpdateRequest;
import com.github.elephant.management.entity.AttachmentEntity;
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

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final BoardRepository boardRepository;
    private final TaskRepository taskRepository;
    private final ResourceRepository resourceRepository;
    private final S3StorageService s3StorageService;
    private final TaskMapper taskMapper;
    private final ResourceMapper resourceMapper;

    @Transactional
    @Override
    public TaskSummaryResponse createTask(TaskCreateRequest request) {
        log.info("Creating task with title = {} and boardId = {}", request.title(), request.boardId());

        BoardEntity board = getBoardByIdOrThrow(request.boardId());

        TaskEntity task = taskMapper.toTaskEntity(request);
        task.setStatus(TaskStatus.PLANNED);

        if (request.attachments() != null && !request.attachments().isEmpty()) {
            for (Long id : request.attachments()) {
                ResourceEntity resource = resourceRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Resource with id = %s not found".formatted(id)));

                AttachmentEntity attachment = new AttachmentEntity();
                attachment.setTask(task);
                attachment.setResource(resource);
            }
        }

        task.setBoard(board);

        return taskMapper.toTaskSummaryResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskSummaryResponse> getAllTasksByBoardId(Long boardId) {
        log.info("Getting all tasks for boardId = {}", boardId);

        BoardEntity board = getBoardByIdOrThrow(boardId);

        return taskRepository.findAllByBoardId(board.getId())
                .stream()
                .map(taskMapper::toTaskSummaryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDetailsResponse getTaskById(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        List<ResourceResponse> attachments = task.getAttachments()
                .stream().map(attachment -> {

                    ResourceEntity resource = attachment.getResource();

                    String presignedUrl = s3StorageService.getPresignedUrl(resource.getKey(), 3600);

                    return resourceMapper.toResourceResponse(resource, presignedUrl, 3600);
                }).toList();

        return taskMapper.toTaskDetailsResponse(task, attachments);
    }

    @Transactional
    @Override
    public TaskSummaryResponse updateTaskById(Long id, TaskUpdateRequest request) {
        log.info("Updating task with id = {}", id);

        TaskEntity task = getTaskByIdOrThrow(id);
        TaskEntity updatedTask = taskMapper.updateTaskEntity(task, request);

        return taskMapper.toTaskSummaryResponse(taskRepository.save(updatedTask));
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
