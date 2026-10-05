package com.vinayakachaviti.family.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyRequest {

    @NotBlank(message = "Family name is required")
    private String familyName;

    private String representativeName;

    private boolean published;
}
