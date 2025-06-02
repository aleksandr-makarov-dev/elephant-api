package com.github.elephant.taskmanagement.repository;

import com.github.elephant.taskmanagement.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
