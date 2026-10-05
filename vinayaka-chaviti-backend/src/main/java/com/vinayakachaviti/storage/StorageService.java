package com.vinayakachaviti.storage;

/**
 * PRD-STORAGE: Abstraction over file/image storage so the rest of the codebase
 * never depends on a specific provider. MySQL only ever stores the returned
 * URL/metadata — never raw file bytes. Swap the implementation (local disk for
 * dev, Azure Blob / AWS S3 / Cloudinary for production) without touching
 * callers in the gallery/video modules.
 */
public interface StorageService {

    /**
     * Stores the given file bytes under the given folder and returns a publicly
     * accessible URL (or path) that can be persisted in the database.
     */
    String store(String folder, String originalFilename, byte[] content, String contentType);

    /**
     * Deletes a previously stored file given the URL/path returned by store().
     */
    void delete(String fileUrl);
}
