package com.vinayakachaviti.gallery.controller;

import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.gallery.dto.*;
import com.vinayakachaviti.gallery.service.GalleryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * US-GALLERY: Public album/image browsing (editorial gallery + lightbox) plus
 * admin album/image management (create album, upload/reorder/caption images,
 * publish/unpublish).
 */
@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@Tag(name = "Gallery", description = "API for gallery albums and images")
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping("/albums")
    @Operation(summary = "List published albums (public)")
    public ResponseEntity<ApiResponse<PageResponse<AlbumResponse>>> getPublishedAlbums(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(galleryService.getPublishedAlbums(PageRequest.of(page, size))));
    }

    @GetMapping("/albums/admin/all")
    @Operation(summary = "List all albums including unpublished (admin only)")
    public ResponseEntity<ApiResponse<PageResponse<AlbumResponse>>> getAllAlbums(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(galleryService.getAllAlbums(PageRequest.of(page, size))));
    }

    @GetMapping("/albums/{albumId}/images")
    @Operation(summary = "List images in an album, in display order (public)")
    public ResponseEntity<ApiResponse<List<ImageResponse>>> getAlbumImages(@PathVariable Long albumId) {
        return ResponseEntity.ok(ApiResponse.success(galleryService.getAlbumImages(albumId)));
    }

    @PostMapping("/albums")
    @Operation(summary = "Create an album (admin only)")
    public ResponseEntity<ApiResponse<AlbumResponse>> createAlbum(@Valid @RequestBody AlbumRequest request) {
        AlbumResponse response = galleryService.createAlbum(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Album created successfully", response));
    }

    @PutMapping("/albums/{id}")
    @Operation(summary = "Rename/update an album (admin only)")
    public ResponseEntity<ApiResponse<AlbumResponse>> updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Album updated successfully", galleryService.renameAlbum(id, request)));
    }

    @PatchMapping("/albums/{id}/publish")
    @Operation(summary = "Publish an album (admin only)")
    public ResponseEntity<ApiResponse<AlbumResponse>> publishAlbum(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Album published", galleryService.setAlbumPublished(id, true)));
    }

    @PatchMapping("/albums/{id}/unpublish")
    @Operation(summary = "Unpublish an album (admin only)")
    public ResponseEntity<ApiResponse<AlbumResponse>> unpublishAlbum(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Album unpublished", galleryService.setAlbumPublished(id, false)));
    }

    @DeleteMapping("/albums/{id}")
    @Operation(summary = "Delete an album and its images (admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteAlbum(@PathVariable Long id) {
        galleryService.deleteAlbum(id);
        return ResponseEntity.ok(ApiResponse.success("Album deleted successfully"));
    }

    @PostMapping(value = "/albums/{albumId}/images", consumes = "multipart/form-data")
    @Operation(summary = "Upload an image to an album (admin only, multipart upload)")
    public ResponseEntity<ApiResponse<ImageResponse>> uploadImage(
            @PathVariable Long albumId,
            @RequestPart("file") MultipartFile file,
            @RequestParam(required = false) String caption
    ) {
        ImageResponse response = galleryService.uploadImage(albumId, file, caption);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Image uploaded successfully", response));
    }

    @PutMapping("/images/{imageId}")
    @Operation(summary = "Update image caption/order (admin only)")
    public ResponseEntity<ApiResponse<ImageResponse>> updateImageMetadata(
            @PathVariable Long imageId, @RequestBody ImageMetadataRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Image updated successfully", galleryService.updateImageMetadata(imageId, request)));
    }

    @PutMapping("/albums/{albumId}/images/reorder")
    @Operation(summary = "Reorder images within an album (admin only, drag-and-drop reorder)")
    public ResponseEntity<ApiResponse<Void>> reorderImages(@PathVariable Long albumId, @RequestBody ReorderRequest request) {
        galleryService.reorderImages(albumId, request);
        return ResponseEntity.ok(ApiResponse.success("Images reordered successfully"));
    }

    @DeleteMapping("/images/{imageId}")
    @Operation(summary = "Delete an image (admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable Long imageId) {
        galleryService.deleteImage(imageId);
        return ResponseEntity.ok(ApiResponse.success("Image deleted successfully"));
    }
}
