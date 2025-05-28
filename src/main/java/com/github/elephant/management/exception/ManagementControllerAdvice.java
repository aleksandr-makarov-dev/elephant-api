package com.github.elephant.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManagementControllerAdvice {

    @ExceptionHandler({BoardNotFoundException.class, TaskNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleNotFoundException(RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
    }

}
