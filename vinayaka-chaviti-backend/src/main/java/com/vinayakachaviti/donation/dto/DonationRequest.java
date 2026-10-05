package com.vinayakachaviti.donation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * US-DONATIONS: Request body for initiating a donation (public endpoint).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationRequest {

    @NotBlank(message = "Donor name is required")
    private String donorName;

    @Email(message = "Must be a valid email address")
    private String email;

    private String phone;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.0", message = "Amount must be at least 1")
    private BigDecimal amount;

    private String currency;

    private String message;

    private boolean anonymous;
}
