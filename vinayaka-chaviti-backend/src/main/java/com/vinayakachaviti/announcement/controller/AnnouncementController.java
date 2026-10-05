package com.vinayakachaviti.announcement.controller;

import com.vinayakachaviti.announcement.dto.AnnouncementRequest;
import com.vinayakachaviti.announcement.dto.AnnouncementResponse;
import com.vinayakachaviti.announcement.service.AnnouncementService;
import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
@Tag(name = "Announcements", description = "API for festival announcements and notices")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    @Operation(summary = "List published announcements, most recent first (public)")
    public ResponseEntity<ApiResponse<PageResponse<AnnouncementResponse>>> getPublishedAnnouncements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(announcementService.getPublishedAnnouncements(pageable)));
    }

    @GetMapping("/admin/all")
    @Operation(summary = "List all announcements including unpublished (admin only)")
    public ResponseEntity<ApiResponse<PageResponse<AnnouncementResponse>>> getAllAnnouncements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(announcementService.getAllAnnouncements(pageable)));
    }

    @PostMapping
    @Operation(summary = "Create an announcement (admin only)")
    public ResponseEntity<ApiResponse<AnnouncementResponse>> createAnnouncement(@Valid @RequestBody AnnouncementRequest request) {
        AnnouncementResponse response = announcementService.createAnnouncement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Announcement created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an announcement (admin only)")
    public ResponseEntity<ApiResponse<AnnouncementResponse>> updateAnnouncement(@PathVariable Long id, @Valid @RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Announcement updated successfully", announcementService.updateAnnouncement(id, request)));
    }

    @PatchMapping("/{id}/publish")
    @Operation(summary = "Publish an announcement (admin only)")
    public ResponseEntity<ApiResponse<AnnouncementResponse>> publishAnnouncement(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Announcement published", announcementService.setPublished(id, true)));
    }

    @PatchMapping("/{id}/unpublish")
    @Operation(summary = "Unpublish an announcement (admin only)")
    public ResponseEntity<ApiResponse<AnnouncementResponse>> unpublishAnnouncement(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Announcement unpublished", announcementService.setPublished(id, false)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an announcement (admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok(ApiResponse.success("Announcement deleted successfully"));
    }
}
