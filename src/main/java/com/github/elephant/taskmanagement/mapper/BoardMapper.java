package com.github.elephant.taskmanagement.mapper;

import com.github.elephant.taskmanagement.dto.BoardCreateRequest;
import com.github.elephant.taskmanagement.dto.BoardResponse;
import com.github.elephant.taskmanagement.dto.BoardUpdateRequest;
import com.github.elephant.taskmanagement.entity.BoardEntity;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public BoardResponse toBoardResponse(BoardEntity entity) {
        return new BoardResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    public BoardEntity toBoardEntity(BoardCreateRequest request) {
        return BoardEntity.builder()
                .title(request.title())
                .description(request.description())
                .build();
    }

    public BoardEntity updateBoardEntity(BoardEntity entity, BoardUpdateRequest request) {
        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setStatus(request.status());

        return entity;
    }
}
