package com.github.elephant.taskmanagement.service;

import com.github.elephant.taskmanagement.dto.AttachmentResponse;
import com.github.elephant.taskmanagement.entity.TaskEntity;

import java.util.List;

public interface AttachmentService {

    void saveAllAttachments(TaskEntity task, List<Long> attachmentIdList);

    List<AttachmentResponse> getAttachmentsByTaskId(Long taskId);
}
