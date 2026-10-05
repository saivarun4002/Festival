package com.vinayakachaviti.event.controller;

import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.EventCategory;
import com.vinayakachaviti.event.dto.EventCreateRequest;
import com.vinayakachaviti.event.dto.EventResponse;
import com.vinayakachaviti.event.dto.EventUpdateRequest;
import com.vinayakachaviti.event.service.FestivalEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Event Management", description = "API for managing festival events and schedules")
public class FestivalEventController {

    private final FestivalEventService eventService;

    @GetMapping
    @Operation(summary = "List/search/filter events (public — published only unless publishedOnly=false)")
    public ResponseEntity<ApiResponse<PageResponse<EventResponse>>> searchEvents(
            @RequestParam(required = false) Long festivalId,
            @RequestParam(required = false) EventCategory category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "true") boolean publishedOnly,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("eventDate").ascending().and(Sort.by("startTime").ascending()));
        PageResponse<EventResponse> response = eventService.searchEvents(festivalId, category, date, publishedOnly, search, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/today")
    @Operation(summary = "Today's published events, ordered by start time (Today at a Glance)")
    public ResponseEntity<ApiResponse<List<EventResponse>>> getTodaysEvents() {
        return ResponseEntity.ok(ApiResponse.success(eventService.getTodaysEvents()));
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Upcoming published events (Next Event section)")
    public ResponseEntity<ApiResponse<List<EventResponse>>> getUpcomingEvents(
            @RequestParam(defaultValue = "5") int limit
    ) {
        Pageable pageable = PageRequest.of(0, limit);
        return ResponseEntity.ok(ApiResponse.success(eventService.getUpcomingEvents(pageable)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event details by ID")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(eventService.getEventById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new event (admin only)")
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@Valid @RequestBody EventCreateRequest request) {
        EventResponse response = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Event created successfully", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an event (admin only)")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(@PathVariable Long id, @Valid @RequestBody EventUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Event updated successfully", eventService.updateEvent(id, request)));
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Mark event as completed (admin only)")
    public ResponseEntity<ApiResponse<EventResponse>> completeEvent(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Event marked as completed", eventService.setCompleted(id, true)));
    }

    @PatchMapping("/{id}/incomplete")
    @Operation(summary = "Mark event as not completed (admin only)")
    public ResponseEntity<ApiResponse<EventResponse>> incompleteEvent(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Event marked as not completed", eventService.setCompleted(id, false)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an event (admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(ApiResponse.success("Event deleted successfully"));
    }
}