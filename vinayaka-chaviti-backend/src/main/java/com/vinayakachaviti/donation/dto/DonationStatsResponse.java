package com.vinayakachaviti.donation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * US-DONATIONS: Public aggregate stats shown on the donation page
 * ("₹X raised so far from Y donors").
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationStatsResponse {
    private BigDecimal totalRaised;
    private long donorCount;
}
