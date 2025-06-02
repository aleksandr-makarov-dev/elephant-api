package com.github.elephant.management.repository;

import com.github.elephant.management.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
