package com.vinayakachaviti.donation.controller;

import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.DonationStatus;
import com.vinayakachaviti.donation.dto.DonationRequest;
import com.vinayakachaviti.donation.dto.DonationResponse;
import com.vinayakachaviti.donation.dto.DonationStatsResponse;
import com.vinayakachaviti.donation.service.DonationService;
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
 * US-DONATIONS: Public endpoints to initiate a donation and view aggregate
 * stats, plus admin-only endpoints to confirm/fail payments (mock gateway)
 * and list all donations.
 */
@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
@Tag(name = "Donations", description = "API for donations/payment (mock gateway in dev)")
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    @Operation(summary = "Initiate a donation (public) — returns a mock payment reference to 'pay' against")
    public ResponseEntity<ApiResponse<DonationResponse>> createDonation(@Valid @RequestBody DonationRequest request) {
        DonationResponse response = donationService.createDonation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Donation initiated successfully", response));
    }

    @GetMapping("/stats")
    @Operation(summary = "Public aggregate donation stats (total raised, donor count)")
    public ResponseEntity<ApiResponse<DonationStatsResponse>> getStats() {
        return ResponseEntity.ok(ApiResponse.success(donationService.getStats()));
    }

    @PatchMapping("/{id}/confirm")
    @Operation(summary = "Confirm a pending donation payment (admin only — simulates gateway webhook)")
    public ResponseEntity<ApiResponse<DonationResponse>> confirmPayment(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Donation confirmed", donationService.confirmPayment(id)));
    }

    @PatchMapping("/{id}/fail")
    @Operation(summary = "Mark a donation payment as failed (admin only)")
    public ResponseEntity<ApiResponse<DonationResponse>> markFailed(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Donation marked as failed", donationService.markFailed(id)));
    }

    @GetMapping("/admin/all")
    @Operation(summary = "List all donations, optionally filtered by status (admin only)")
    public ResponseEntity<ApiResponse<PageResponse<DonationResponse>>> getAll(
            @RequestParam(required = false) DonationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<DonationResponse> response = status != null
                ? donationService.getDonationsByStatus(status, pageable)
                : donationService.getAllDonations(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
