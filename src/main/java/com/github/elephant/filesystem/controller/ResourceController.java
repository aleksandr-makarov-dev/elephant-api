package com.github.elephant.filesystem.controller;

import com.github.elephant.filesystem.dto.ResourceDownloadResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.dto.ResourceUploadResponse;
import com.github.elephant.filesystem.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResourceUploadResponse> updateResource(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.uploadResource(file));
    }

    @GetMapping("download/{id}")
    public ResponseEntity<ResourceDownloadResponse> downloadResource(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.downloadResourceById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResourceResponse>> getAllResources() {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.getAllResources());
    }
}
