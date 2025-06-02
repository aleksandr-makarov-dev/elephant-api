package com.github.elephant.filesystem.controller;

import com.github.elephant.filesystem.dto.ResourcePresignedUrlResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing file resources like uploads and downloads.
 */
@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
@Tag(name = "Resources", description = "File upload and download endpoints")
public class ResourceController {

    private final ResourceService resourceService;

    @Operation(
            summary = "Upload a file resource",
            description = "Uploads a file and returns metadata about the stored resource"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or request")
    })
    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResourceResponse> updateResource(
            @Parameter(description = "File to upload", required = true)
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.uploadResource(file));
    }

    @Operation(
            summary = "Get presigned URL for downloading a file",
            description = "Generates and returns a presigned URL to download the file by resource ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presigned URL generated successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @GetMapping("download/{id}")
    public ResponseEntity<ResourcePresignedUrlResponse> downloadResource(
            @Parameter(description = "ID of the file resource to download", required = true)
            @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.getPresignedUrlById(id));
    }
}
