package com.github.elephant.filesystem.service;

import com.github.elephant.filesystem.dto.ResourceDownloadResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import com.github.elephant.filesystem.dto.ResourceUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ResourceService {

    ResourceUploadResponse uploadResource(MultipartFile file);

    ResourceDownloadResponse downloadResourceById(UUID id);

    List<ResourceResponse> getAllResources();
}
