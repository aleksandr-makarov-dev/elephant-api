package com.github.elephant.filesystem.repository;

import com.github.elephant.filesystem.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {
}
