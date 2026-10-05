package com.vinayakachaviti.festival.dto;

import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.vinayakachaviti.common.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FestivalResponse {
    private Long id;
    private String name;
    private Integer year;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String description;
    private Status status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}