package com.github.elephant.filesystem.controller;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResourceResponse> updateResource(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.uploadResource(file));
    }

    @GetMapping("download/{id}")
    public ResponseEntity<ResourcePresignedUrlResponse> downloadResource(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.getPresignedUrlById(id));
    }
}
