package com.vinayakachaviti.donation.dto;

import com.vinayakachaviti.common.enums.DonationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationResponse {
    private Long id;
    private String donorName;
    private String email;
    private String phone;
    private BigDecimal amount;
    private String currency;
    private DonationStatus status;
    private String paymentReference;
    private String message;
    private boolean anonymous;
    private OffsetDateTime createdAt;
}
