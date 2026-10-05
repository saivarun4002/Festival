package com.vinayakachaviti.family.controller;

import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.family.dto.FamilyRequest;
import com.vinayakachaviti.family.dto.FamilyResponse;
import com.vinayakachaviti.family.service.FamilyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/families")
@RequiredArgsConstructor
@Tag(name = "Family Management", description = "API for managing participating families (Community directory)")
public class FamilyController {

    private final FamilyService familyService;

    @GetMapping
    @Operation(summary = "List published families (public Community directory)")
    public ResponseEntity<ApiResponse<PageResponse<FamilyResponse>>> getPublishedFamilies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("familyName").ascending());
        return ResponseEntity.ok(ApiResponse.success(familyService.getPublishedFamilies(pageable)));
    }

    @GetMapping("/count")
    @Operation(summary = "Published family count (Community stats: '120+ Families')")
    public ResponseEntity<ApiResponse<Long>> getPublishedFamilyCount() {
        return ResponseEntity.ok(ApiResponse.success(familyService.getPublishedFamilyCount()));
    }

    @GetMapping("/admin/all")
    @Operation(summary = "List all families including unpublished (admin only)")
    public ResponseEntity<ApiResponse<PageResponse<FamilyResponse>>> getAllFamilies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("familyName").ascending());
        return ResponseEntity.ok(ApiResponse.success(familyService.getAllFamilies(pageable)));
    }

    @PostMapping
    @Operation(summary = "Add a family (admin only)")
    public ResponseEntity<ApiResponse<FamilyResponse>> createFamily(@Valid @RequestBody FamilyRequest request) {
        FamilyResponse response = familyService.createFamily(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Family added successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a family (admin only)")
    public ResponseEntity<ApiResponse<FamilyResponse>> updateFamily(@PathVariable Long id, @Valid @RequestBody FamilyRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Family updated successfully", familyService.updateFamily(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a family (admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteFamily(@PathVariable Long id) {
        familyService.deleteFamily(id);
        return ResponseEntity.ok(ApiResponse.success("Family deleted successfully"));
    }
}
