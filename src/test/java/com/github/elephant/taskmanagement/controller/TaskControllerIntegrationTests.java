package com.github.elephant.taskmanagement.controller;

import com.github.elephant.taskmanagement.entity.BoardEntity;
import com.github.elephant.taskmanagement.entity.BoardStatus;
import com.github.elephant.taskmanagement.repository.BoardRepository;
import com.github.elephant.taskmanagement.repository.TaskRepository;
import com.github.elephant.taskmanagement.service.TaskService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class TaskControllerIntegrationTests {

    static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17-alpine");
    static final MinIOContainer minioContainer = new MinIOContainer("minio/minio")
            .withUserName("testuser")
            .withPassword("testpassword");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);

        registry.add("minio.endpoint", minioContainer::getS3URL);
        registry.add("minio.access-key", minioContainer::getUserName);
        registry.add("minio.secret-key", minioContainer::getPassword);
    }

    @Value("${spring.minio.bucket}")
    private String bucket;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
        minioContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
        minioContainer.stop();
    }

    @BeforeEach
    void beforeEach() throws Exception {
//        boolean bucketFound = minioClient.bucketExists(BucketExistsArgs.builder()
//                .bucket(bucket)
//                .build());
//
//        if (!bucketFound) {
//            minioClient.makeBucket(MakeBucketArgs.builder()
//                    .bucket(bucket)
//                    .build());
//        }

        taskRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return task summary response when creating a task with valid input without attachments")
    public void givenValidInput_whenCreatingTask_thenReturnTaskSummaryResponse() throws Exception {

    }
}
