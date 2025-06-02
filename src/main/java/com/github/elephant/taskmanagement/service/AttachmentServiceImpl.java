package com.github.elephant.taskmanagement.service;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;
import com.github.elephant.filesystem.entity.ResourceEntity;
import com.github.elephant.filesystem.service.ResourceService;
import com.github.elephant.taskmanagement.dto.AttachmentResponse;
import com.github.elephant.taskmanagement.entity.AttachmentEntity;
import com.github.elephant.taskmanagement.entity.TaskEntity;
import com.github.elephant.taskmanagement.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final ResourceService resourceService;

    @Transactional
    @Override
    public void saveAllAttachments(TaskEntity task, List<Long> attachmentIdList) {

        List<AttachmentEntity> attachments = attachmentIdList
                .stream()
                .map(id -> {
                    ResourceEntity resource = resourceService.getResourceEntityByIdOrThrow(id);
                    AttachmentEntity attachment = new AttachmentEntity();
                    attachment.setTask(task);
                    attachment.setResource(resource);

                    return attachment;
                }).toList();

        attachmentRepository.saveAll(attachments);
    }

    @Transactional
    @Override
    public List<AttachmentResponse> getAttachmentsByTaskId(Long taskId) {
        return attachmentRepository.findAllByTaskId(taskId)
                .stream()
                .map((attachment) -> {
                    ResourceEntity resource = attachment.getResource();
                    ResourcePresignedUrlResponse presignedUrl = resourceService.getPresignedUrlById(resource.getId());

                    return new AttachmentResponse(
                            resource.getId(),
                            resource.getName(),
                            resource.getExtension(),
                            resource.getSize(),
                            presignedUrl
                    );
                })
                .toList();
    }

}
