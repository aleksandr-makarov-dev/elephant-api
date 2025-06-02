package com.github.elephant.taskmanagement.service;

import com.github.elephant.taskmanagement.dto.AttachmentResponse;
import com.github.elephant.taskmanagement.entity.TaskEntity;

import java.util.List;

/**
 * Service interface for managing task attachments.
 */
public interface AttachmentService {

    /**
     * Saves all attachments for the given task using the list of resource IDs.
     *
     * @param task             the task entity to associate attachments with
     * @param attachmentIdList the list of resource IDs to be attached to the task
     */
    void saveAllAttachments(TaskEntity task, List<Long> attachmentIdList);

    /**
     * Retrieves all attachments associated with the specified task.
     *
     * @param taskId the ID of the task
     * @return a list of attachment response objects containing metadata and download URLs
     */
    List<AttachmentResponse> getAttachmentsByTaskId(Long taskId);
}
