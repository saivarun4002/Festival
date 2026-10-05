package com.vinayakachaviti.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * PRD-STORAGE: Default local-disk StorageService implementation for development.
 * Files are written under {storage.base-path}/{folder}/ and served back via the
 * {storage.public-base-url} prefix. Swap this bean out for an Azure/S3/Cloudinary
 * implementation in production by providing an alternate StorageService bean —
 * no changes needed in the gallery/video modules that depend on the interface.
 */
@Service
@Slf4j
public class LocalDiskStorageService implements StorageService {

    private final Path basePath;
    private final String publicBaseUrl;

    public LocalDiskStorageService(
            @Value("${storage.base-path:./uploads}") String basePath,
            @Value("${storage.public-base-url:http://localhost:8080/uploads}") String publicBaseUrl
    ) {
        this.basePath = Paths.get(basePath).toAbsolutePath().normalize();
        this.publicBaseUrl = publicBaseUrl;
        try {
            Files.createDirectories(this.basePath);
        } catch (IOException e) {
            log.error("Unable to create storage base path {}", this.basePath, e);
        }
    }

    @Override
    public String store(String folder, String originalFilename, byte[] content, String contentType) {
        try {
            String safeFolder = StringUtils.hasText(folder) ? folder.replaceAll("[^a-zA-Z0-9/_-]", "") : "misc";
            Path folderPath = basePath.resolve(safeFolder);
            Files.createDirectories(folderPath);

            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            }
            String fileName = UUID.randomUUID() + extension;
            Path target = folderPath.resolve(fileName);
            Files.write(target, content);

            return publicBaseUrl + "/" + safeFolder + "/" + fileName;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to store file: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String fileUrl) {
        if (fileUrl == null || !fileUrl.startsWith(publicBaseUrl)) {
            return;
        }
        try {
            String relative = fileUrl.substring(publicBaseUrl.length()).replaceFirst("^/", "");
            Path target = basePath.resolve(relative);
            Files.deleteIfExists(target);
        } catch (IOException e) {
            log.warn("Failed to delete file {}: {}", fileUrl, e.getMessage());
        }
    }
}
