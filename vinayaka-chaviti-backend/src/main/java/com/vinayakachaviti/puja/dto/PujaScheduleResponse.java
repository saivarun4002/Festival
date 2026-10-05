package com.vinayakachaviti.puja.dto;

import com.vinayakachaviti.common.enums.PujaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PujaScheduleResponse {
    private Long id;
    private String title;
    private String description;
    private PujaType pujaType;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private Integer durationMinutes;
    private boolean published;
}
