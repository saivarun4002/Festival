package com.vinayakachaviti.event.dto;

import com.vinayakachaviti.common.enums.EventCategory;
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
public class EventResponse {
    private Long id;
    private Long festivalId;
    private String title;
    private String description;
    private EventCategory category;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private String imageUrl;
    private boolean published;
    private boolean completed;
}