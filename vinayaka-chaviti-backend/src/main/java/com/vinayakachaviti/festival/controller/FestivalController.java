package com.vinayakachaviti.festival.controller;

import com.vinayakachaviti.festival.dto.FestivalCreateRequest;
import com.vinayakachaviti.festival.dto.FestivalUpdateRequest;
import com.vinayakachaviti.festival.dto.FestivalResponse;
import com.vinayakachaviti.festival.service.FestivalService;
import com.vinayakachaviti.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/festivals")
@RequiredArgsConstructor
@Tag(name = "Festival Management", description = "API for managing festivals")
public class FestivalController {

    private final FestivalService festivalService;

    @PostMapping
    @Operation(summary = "Create a new festival")
    public ResponseEntity<ApiResponse<FestivalResponse>> createFestival(@Valid @RequestBody FestivalCreateRequest request) {
        FestivalResponse response = festivalService.createFestival(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Festival created successfully", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a festival by ID")
    public ResponseEntity<ApiResponse<FestivalResponse>> getFestivalById(@PathVariable Long id) {
        FestivalResponse response = festivalService.getFestivalById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/year/{year}")
    @Operation(summary = "Get a festival by year")
    public ResponseEntity<ApiResponse<FestivalResponse>> getFestivalByYear(@PathVariable Integer year) {
        FestivalResponse response = festivalService.getFestivalByYear(year);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/current")
    @Operation(summary = "Get current festival")
    public ResponseEntity<ApiResponse<FestivalResponse>> getCurrentFestival() {
        FestivalResponse response = festivalService.getCurrentFestival();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a festival")
    public ResponseEntity<ApiResponse<FestivalResponse>> updateFestival(@PathVariable Long id, @Valid @RequestBody FestivalUpdateRequest request) {
        FestivalResponse response = festivalService.updateFestival(id, request);
        return ResponseEntity.ok(ApiResponse.success("Festival updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a festival")
    public ResponseEntity<ApiResponse<Void>> deleteFestival(@PathVariable Long id) {
        festivalService.deleteFestival(id);
        return ResponseEntity.ok(ApiResponse.success("Festival deleted successfully"));
    }
}
