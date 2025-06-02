package com.github.elephant.taskmanagement.repository;

import com.github.elephant.taskmanagement.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {
    List<AttachmentEntity> findAllByTaskId(Long taskId);
}
