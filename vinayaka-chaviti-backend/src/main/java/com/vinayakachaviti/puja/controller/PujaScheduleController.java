package com.vinayakachaviti.puja.controller;

import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.puja.dto.PujaScheduleRequest;
import com.vinayakachaviti.puja.dto.PujaScheduleResponse;
import com.vinayakachaviti.puja.service.PujaScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * US-PUJA: Public read endpoints for puja schedule (list/today) plus
 * admin-only create/update/publish/delete.
 */
@RestController
@RequestMapping("/api/puja/schedules")
@RequiredArgsConstructor
@Tag(name = "Puja Schedule", description = "API for managing puja/spiritual schedule entries")
public class PujaScheduleController {

    private final PujaScheduleService service;

    @GetMapping
    @Operation(summary = "List published puja schedule entries (public)")
    public ResponseEntity<ApiResponse<PageResponse<PujaScheduleResponse>>> getPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(service.getPublishedSchedules(pageable)));
    }

    @GetMapping("/today")
    @Operation(summary = "Today's published puja schedule entries (public)")
    public ResponseEntity<ApiResponse<List<PujaScheduleResponse>>> getTodaysSchedule() {
        return ResponseEntity.ok(ApiResponse.success(service.getTodaysSchedule()));
    }

    @GetMapping("/admin/all")
    @Operation(summary = "List all puja schedule entries including unpublished (admin only)")
    public ResponseEntity<ApiResponse<PageResponse<PujaScheduleResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(service.getAllSchedules(pageable)));
    }

    @PostMapping
    @Operation(summary = "Create a puja schedule entry (admin only)")
    public ResponseEntity<ApiResponse<PujaScheduleResponse>> create(@Valid @RequestBody PujaScheduleRequest request) {
        PujaScheduleResponse response = service.createSchedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Puja schedule created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a puja schedule entry (admin only)")
    public ResponseEntity<ApiResponse<PujaScheduleResponse>> update(@PathVariable Long id, @Valid @RequestBody PujaScheduleRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Puja schedule updated successfully", service.updateSchedule(id, request)));
    }

    @PatchMapping("/{id}/publish")
    @Operation(summary = "Publish a puja schedule entry (admin only)")
    public ResponseEntity<ApiResponse<PujaScheduleResponse>> publish(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Puja schedule published", service.setPublished(id, true)));
    }

    @PatchMapping("/{id}/unpublish")
    @Operation(summary = "Unpublish a puja schedule entry (admin only)")
    public ResponseEntity<ApiResponse<PujaScheduleResponse>> unpublish(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Puja schedule unpublished", service.setPublished(id, false)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a puja schedule entry (admin only)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deleteSchedule(id);
        return ResponseEntity.ok(ApiResponse.success("Puja schedule deleted successfully"));
    }
}
