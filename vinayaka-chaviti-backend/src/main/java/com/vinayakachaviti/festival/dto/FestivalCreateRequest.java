package com.vinayakachaviti.festival.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request DTO for creating a new Festival.
 * Note: cross-field validation (endDate must not be before startDate) is enforced in the service layer.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FestivalCreateRequest {

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 150)
    private String name;

    @NotNull
    @Min(2000)
    @Max(2100)
    private Integer year;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Size(max = 255)
    private String location;

    @Size(max = 5000)
    private String description;
}
