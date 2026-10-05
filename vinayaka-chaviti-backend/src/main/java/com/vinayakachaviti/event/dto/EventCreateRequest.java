package com.vinayakachaviti.event.dto;

import com.vinayakachaviti.common.enums.EventCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * US-EVENTS, PRD: Event creation payload used by the admin Events CRUD form.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateRequest {

    @NotNull(message = "Festival ID is required")
    private Long festivalId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Category is required")
    private EventCategory category;

    @NotNull(message = "Event date is required")
    private LocalDate eventDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private String imageUrl;

    private boolean published;
}
