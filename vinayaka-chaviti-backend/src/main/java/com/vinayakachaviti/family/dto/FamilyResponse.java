package com.vinayakachaviti.family.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyResponse {
    private Long id;
    private String familyName;
    private String representativeName;
    private boolean published;
}
