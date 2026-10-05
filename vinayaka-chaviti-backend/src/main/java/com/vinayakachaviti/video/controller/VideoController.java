package com.vinayakachaviti.video.controller;

import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.VideoCategory;
import com.vinayakachaviti.video.dto.VideoRequest;
import com.vinayakachaviti.video.dto.VideoResponse;
import com.vinayakachaviti.video.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@Tag(name = "Videos", description = "API for the video hub (YouTube-backed)")
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    @Operation(summary = "List published videos, optionally filtered by category (public)")
    public ResponseEntity<ApiResponse<PageResponse<VideoResponse>>> getPublishedVideos(
            @RequestParam(required = false) VideoCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(videoService.getPublishedVideos(category, PageRequest.of(page, size))));
    }

    @GetMapping("/admin/all")
    @Operation(summary = "List all videos including unpublished (admin only)")
    public ResponseEntity<ApiResponse<PageResponse<VideoResponse>>> getAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(videoService.getAllVideos(PageRequest.of(page, size))));
    }

    @PostMapping
    @Operation(summary = "Add a video (admin only)")
    public ResponseEntity<ApiResponse<VideoResponse>> createVideo(@Valid @RequestBody VideoRequest request) {
        VideoResponse response = videoService.createVideo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Video added successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a video (admin only)")
    public ResponseEntity<ApiResponse<VideoResponse>> updateVideo(@PathVariable Long id, @Valid @RequestBody VideoRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Video updated successfully", videoService.updateVideo(id, request)));
    }

    @PatchMapping("/{id}/publish")
    @Operation(summary = "Publish a video (admin only)")
    public ResponseEntity<ApiResponse<VideoResponse>> publishVideo(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Video published", videoService.setPublished(id, true)));
    }

    @PatchMapping("/{id}/unpublish")
    @Operation(summary = "Unpublish a video (admin only)")
    public ResponseEntity<ApiResponse<VideoResponse>> unpublishVideo(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Video unpublished", videoService.setPublished(id, false)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a video (admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.ok(ApiResponse.success("Video deleted successfully"));
    }
}
