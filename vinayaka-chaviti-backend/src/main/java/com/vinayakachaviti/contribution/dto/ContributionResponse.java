package com.vinayakachaviti.contribution.dto;

import com.vinayakachaviti.common.enums.ContributionCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContributionResponse {
    private Long id;
    private ContributionCategory category;
    private String eventLabel;
    private String itemName;
    private String donorName;
    private String designation;
    private String quantity;
    private Integer displayOrder;
    private OffsetDateTime createdAt;
}
