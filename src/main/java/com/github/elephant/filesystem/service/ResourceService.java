package com.github.elephant.filesystem.service;

import com.github.elephant.filesystem.dto.ResourceDownloadResponse;
import com.github.elephant.filesystem.dto.ResourceResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {

    ResourceResponse uploadResource(MultipartFile file);

    ResourceDownloadResponse downloadResourceById(Long id);
}
