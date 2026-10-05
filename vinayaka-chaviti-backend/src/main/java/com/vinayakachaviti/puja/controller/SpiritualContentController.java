package com.vinayakachaviti.puja.controller;

import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.SpiritualContentCategory;
import com.vinayakachaviti.puja.dto.SpiritualContentRequest;
import com.vinayakachaviti.puja.dto.SpiritualContentResponse;
import com.vinayakachaviti.puja.service.SpiritualContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * US-PUJA: Public read endpoints for spiritual content (mantras/stories/
 * significance/vratam procedures) plus admin-only create/update/publish/delete.
 */
@RestController
@RequestMapping("/api/puja/content")
@RequiredArgsConstructor
@Tag(name = "Spiritual Content", description = "API for managing mantras, stories, and spiritual significance content")
public class SpiritualContentController {

    private final SpiritualContentService service;

    @GetMapping
    @Operation(summary = "List published spiritual content, optionally filtered by category (public)")
    public ResponseEntity<ApiResponse<PageResponse<SpiritualContentResponse>>> getPublished(
            @RequestParam(required = false) SpiritualContentCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(service.getPublishedContent(category, pageable)));
    }

    @GetMapping("/admin/all")
    @Operation(summary = "List all spiritual content including unpublished (admin only)")
    public ResponseEntity<ApiResponse<PageResponse<SpiritualContentResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(service.getAllContent(pageable)));
    }

    @PostMapping
    @Operation(summary = "Create spiritual content (admin only)")
    public ResponseEntity<ApiResponse<SpiritualContentResponse>> create(@Valid @RequestBody SpiritualContentRequest request) {
        SpiritualContentResponse response = service.createContent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Content created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update spiritual content (admin only)")
    public ResponseEntity<ApiResponse<SpiritualContentResponse>> update(@PathVariable Long id, @Valid @RequestBody SpiritualContentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Content updated successfully", service.updateContent(id, request)));
    }

    @PatchMapping("/{id}/publish")
    @Operation(summary = "Publish spiritual content (admin only)")
    public ResponseEntity<ApiResponse<SpiritualContentResponse>> publish(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Content published", service.setPublished(id, true)));
    }

    @PatchMapping("/{id}/unpublish")
    @Operation(summary = "Unpublish spiritual content (admin only)")
    public ResponseEntity<ApiResponse<SpiritualContentResponse>> unpublish(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Content unpublished", service.setPublished(id, false)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete spiritual content (admin only)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deleteContent(id);
        return ResponseEntity.ok(ApiResponse.success("Content deleted successfully"));
    }
}
