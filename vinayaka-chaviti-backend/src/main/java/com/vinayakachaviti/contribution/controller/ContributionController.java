package com.vinayakachaviti.contribution.controller;

import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.ContributionCategory;
import com.vinayakachaviti.contribution.dto.ContributionRequest;
import com.vinayakachaviti.contribution.dto.ContributionResponse;
import com.vinayakachaviti.contribution.service.ContributionService;
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
 * US-CONTRIBUTIONS: Public read-only endpoints to list in-kind contribution
 * records (grouped by category tab, optionally searched by donor name),
 * plus admin-only CRUD mutation endpoints.
 */
@RestController
@RequestMapping("/api/contributions")
@RequiredArgsConstructor
@Tag(name = "Contributions", description = "API for in-kind/special donation contribution records")
public class ContributionController {

    private final ContributionService contributionService;

    @GetMapping
    @Operation(summary = "List contributions by category, optionally searched by donor name (public)")
    public ResponseEntity<ApiResponse<PageResponse<ContributionResponse>>> getByCategory(
            @RequestParam ContributionCategory category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "500") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(contributionService.getByCategory(category, search, pageable)));
    }

    @GetMapping("/admin/all")
    @Operation(summary = "List all contributions across categories (admin only)")
    public ResponseEntity<ApiResponse<PageResponse<ContributionResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "500") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(contributionService.getAll(pageable)));
    }

    @PostMapping
    @Operation(summary = "Create a contribution record (admin only)")
    public ResponseEntity<ApiResponse<ContributionResponse>> create(@Valid @RequestBody ContributionRequest request) {
        ContributionResponse response = contributionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Contribution created", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a contribution record (admin only)")
    public ResponseEntity<ApiResponse<ContributionResponse>> update(@PathVariable Long id, @Valid @RequestBody ContributionRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Contribution updated", contributionService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a contribution record (admin only)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        contributionService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Contribution deleted", null));
    }
}
