package com.vinayakachaviti.donation.repository;

import com.vinayakachaviti.common.enums.DonationStatus;
import com.vinayakachaviti.donation.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    Page<Donation> findByStatusOrderByCreatedAtDesc(DonationStatus status, Pageable pageable);

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d WHERE d.status = 'COMPLETED'")
    BigDecimal getTotalRaised();

    long countByStatus(DonationStatus status);
}
