package com.vinayakachaviti.donation.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.DonationStatus;
import com.vinayakachaviti.donation.dto.DonationRequest;
import com.vinayakachaviti.donation.dto.DonationResponse;
import com.vinayakachaviti.donation.dto.DonationStatsResponse;
import org.springframework.data.domain.Pageable;

public interface DonationService {
    DonationResponse createDonation(DonationRequest request);
    DonationResponse confirmPayment(Long id);
    DonationResponse markFailed(Long id);
    DonationStatsResponse getStats();
    PageResponse<DonationResponse> getDonationsByStatus(DonationStatus status, Pageable pageable);
    PageResponse<DonationResponse> getAllDonations(Pageable pageable);
}