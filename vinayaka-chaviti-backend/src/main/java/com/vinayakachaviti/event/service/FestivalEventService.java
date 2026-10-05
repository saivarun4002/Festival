package com.vinayakachaviti.event.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.EventCategory;
import com.vinayakachaviti.event.dto.EventCreateRequest;
import com.vinayakachaviti.event.dto.EventResponse;
import com.vinayakachaviti.event.dto.EventUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FestivalEventService {
    EventResponse createEvent(EventCreateRequest request);
    EventResponse getEventById(Long id);
    PageResponse<EventResponse> searchEvents(Long festivalId, EventCategory category, LocalDate date, boolean publishedOnly, String search, Pageable pageable);
    List<EventResponse> getTodaysEvents();
    List<EventResponse> getUpcomingEvents(Pageable pageable);
    EventResponse updateEvent(Long id, EventUpdateRequest request);
    EventResponse setPublished(Long id, boolean published);
    void deleteEvent(Long id);
    EventResponse setCompleted(Long id, boolean completed);
}