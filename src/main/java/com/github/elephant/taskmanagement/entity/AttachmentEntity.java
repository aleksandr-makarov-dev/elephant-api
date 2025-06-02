package com.github.elephant.taskmanagement.entity;

import com.github.elephant.filesystem.entity.ResourceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "attachments")
public class AttachmentEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private TaskEntity task;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private ResourceEntity resource;

    public void setTask(TaskEntity task) {
        this.task = task;
        task.getAttachments().add(this);
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
        resource.getAttachments().add(this);
    }
}
