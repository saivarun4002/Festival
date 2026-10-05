package com.vinayakachaviti.donation.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.DonationStatus;
import com.vinayakachaviti.donation.dto.DonationRequest;
import com.vinayakachaviti.donation.dto.DonationResponse;
import com.vinayakachaviti.donation.dto.DonationStatsResponse;
import com.vinayakachaviti.donation.entity.Donation;
import com.vinayakachaviti.donation.repository.DonationRepository;
import com.vinayakachaviti.exception.BadRequestException;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository repository;

    @Override
    @Transactional
    public DonationResponse createDonation(DonationRequest request) {
        Donation donation = Donation.builder()
                .donorName(request.getDonorName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .amount(request.getAmount())
                .currency(request.getCurrency() != null ? request.getCurrency() : "INR")
                .status(DonationStatus.PENDING)
                .paymentReference(generatePaymentReference())
                .message(request.getMessage())
                .anonymous(request.isAnonymous())
                .build();
        donation = repository.save(donation);
        return toResponse(donation);
    }

    @Override
    @Transactional
    public DonationResponse confirmPayment(Long id) {
        Donation donation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation", "id", id));
        if (donation.getStatus() != DonationStatus.PENDING) {
            throw new BadRequestException("Only pending donations can be confirmed");
        }
        donation.setStatus(DonationStatus.COMPLETED);
        donation = repository.save(donation);
        return toResponse(donation);
    }

    @Override
    @Transactional
    public DonationResponse markFailed(Long id) {
        Donation donation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation", "id", id));
        donation.setStatus(DonationStatus.FAILED);
        donation = repository.save(donation);
        return toResponse(donation);
    }

    @Override
    @Transactional(readOnly = true)
    public DonationStatsResponse getStats() {
        BigDecimal totalRaised = repository.getTotalRaised();
        long donorCount = repository.countByStatus(DonationStatus.COMPLETED);
        return new DonationStatsResponse(totalRaised, donorCount);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DonationResponse> getDonationsByStatus(DonationStatus status, Pageable pageable) {
        Page<Donation> page = repository.findByStatusOrderByCreatedAtDesc(status, pageable);
        Page<DonationResponse> responsePage = page.map(donation -> toResponse(donation));
        PageResponse<DonationResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DonationResponse> getAllDonations(Pageable pageable) {
        Page<Donation> page = repository.findAll(pageable);
        Page<DonationResponse> responsePage = page.map(donation -> toResponse(donation));
        PageResponse<DonationResponse> result = PageResponse.from(responsePage);
        return result;
    }

    private String generatePaymentReference() {
        return "MOCKPAY-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }

    private DonationResponse toResponse(Donation donation) {
        return new DonationResponse(
                donation.getId(),
                donation.isAnonymous() ? "Anonymous" : donation.getDonorName(),
                donation.getEmail(),
                donation.getPhone(),
                donation.getAmount(),
                donation.getCurrency(),
                donation.getStatus(),
                donation.getPaymentReference(),
                donation.getMessage(),
                donation.isAnonymous(),
                donation.getCreatedAt()
        );
    }
}