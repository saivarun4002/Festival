package com.vinayakachaviti.contribution.dto;

import com.vinayakachaviti.common.enums.ContributionCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContributionRequest {

    @NotNull(message = "Category is required")
    private ContributionCategory category;

    private String eventLabel;

    @NotBlank(message = "Item name is required")
    private String itemName;

    @NotBlank(message = "Donor name is required")
    private String donorName;

    private String designation;

    private String quantity;

    private Integer displayOrder;
}
