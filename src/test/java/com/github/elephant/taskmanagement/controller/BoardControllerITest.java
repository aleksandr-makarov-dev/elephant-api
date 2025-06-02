package com.github.elephant.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.elephant.taskmanagement.dto.BoardCreateRequest;
import com.github.elephant.taskmanagement.dto.BoardUpdateRequest;
import com.github.elephant.taskmanagement.entity.BoardEntity;
import com.github.elephant.taskmanagement.entity.BoardStatus;
import com.github.elephant.taskmanagement.repository.BoardRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class BoardControllerITest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return a board response when creating a board with valid input")
    void givenValidBoardCreateRequest_whenCreateBoard_thenReturnBoardResponse() throws Exception {
        // given
        BoardCreateRequest request = new BoardCreateRequest("My board", "My board description");

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.title").value(request.title()))
                .andExpect(jsonPath("$.description").value(request.description()))
                .andExpect(jsonPath("$.status").value(BoardStatus.DRAFT.name()));
    }

    @Test
    @DisplayName("Should return collection of board responses")
    void given_whenGetAllBoards_thenReturnBoardResponses() throws Exception {
        // given
        List<BoardEntity> boards = List.of(
                BoardEntity.builder()
                        .title("My board 1")
                        .description("My board description")
                        .status(BoardStatus.DRAFT)
                        .build(),
                BoardEntity.builder()
                        .title("My board 2")
                        .description("My board description")
                        .status(BoardStatus.DRAFT)
                        .build());

        boardRepository.saveAll(boards);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/boards"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(boards.size()));
    }


    @Test
    @DisplayName("Should return updated board response when updating board by id with valid input")
    void givenValidBoardUpdateRequestAndId_whenUpdateBoardById_thenReturnUpdatedBoardResponse() throws Exception {
        // given
        BoardEntity board = BoardEntity.builder()
                .title("My board")
                .description("My board description")
                .status(BoardStatus.DRAFT)
                .build();

        boardRepository.save(board);

        Long boardId = board.getId();
        BoardUpdateRequest request = new BoardUpdateRequest("Updated title", "Updated description", BoardStatus.PUBLISHED);

        // when
        ResultActions response = mockMvc.perform(put("/api/v1/boards/{id}", boardId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title").value(request.title()))
                .andExpect(jsonPath("$.description").value(request.description()))
                .andExpect(jsonPath("$.status").value(BoardStatus.PUBLISHED.name()));
    }

    @Test
    @DisplayName("Should return status 204 no content when try to delete board by id with valid id")
    void givenValidBoardId_whenDeleteBoardById_thenReturnStatus204() throws Exception {
        // given
        BoardEntity board = BoardEntity.builder()
                .title("My board")
                .description("My board description")
                .status(BoardStatus.DRAFT)
                .build();

        boardRepository.save(board);

        Long boardId = board.getId();

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/boards/{id}", boardId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(board)));

        // then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return status 404 when try to delete board with invalid id")
    void givenInvalidBoardId_whenDeleteBoardById_thenReturnStatus404() throws Exception {
        // given
        BoardEntity board = BoardEntity.builder()
                .title("My board")
                .description("My board description")
                .status(BoardStatus.DRAFT)
                .build();

        boardRepository.save(board);

        Long boardId = 999L;

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/boards/{id}", boardId));

        // then
        response.andExpect(status().isNotFound());
    }
}