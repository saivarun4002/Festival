package com.vinayakachaviti.event.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.EventCategory;
import com.vinayakachaviti.event.dto.EventCreateRequest;
import com.vinayakachaviti.event.dto.EventResponse;
import com.vinayakachaviti.event.dto.EventUpdateRequest;
import com.vinayakachaviti.event.entity.FestivalEvent;
import com.vinayakachaviti.event.repository.FestivalEventRepository;
import com.vinayakachaviti.exception.BadRequestException;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import com.vinayakachaviti.festival.entity.Festival;
import com.vinayakachaviti.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalEventServiceImpl implements FestivalEventService {

    private final FestivalEventRepository eventRepository;
    private final FestivalRepository festivalRepository;

    @Override
    @Transactional
    public EventResponse createEvent(EventCreateRequest request) {
        Festival festival = festivalRepository.findById(request.getFestivalId())
                .orElseThrow(() -> new ResourceNotFoundException("Festival", "id", request.getFestivalId()));
        validateTimes(request.getStartTime(), request.getEndTime());
        FestivalEvent event = FestivalEvent.builder()
                .festival(festival)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .eventDate(request.getEventDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .location(request.getLocation())
                .imageUrl(request.getImageUrl())
                .published(request.isPublished())
                .build();
        event = eventRepository.save(event);
        return toResponse(event);
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponse getEventById(Long id) {
        FestivalEvent event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        return toResponse(event);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<EventResponse> searchEvents(
            Long festivalId, EventCategory category, LocalDate date, boolean publishedOnly, String search, Pageable pageable
    ) {
        Page<FestivalEvent> page = eventRepository.search(festivalId, category, date, publishedOnly, search, pageable);
        Page<EventResponse> responsePage = page.map(event -> toResponse(event));
        PageResponse<EventResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getTodaysEvents() {
        return eventRepository.findTodaysEvents(LocalDate.now())
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getUpcomingEvents(Pageable pageable) {
        return eventRepository.findUpcomingEvents(LocalDate.now(), pageable)
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public EventResponse updateEvent(Long id, EventUpdateRequest request) {
        FestivalEvent event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        validateTimes(request.getStartTime(), request.getEndTime());
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setEventDate(request.getEventDate());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setLocation(request.getLocation());
        event.setImageUrl(request.getImageUrl());
        event.setPublished(request.isPublished());
        event = eventRepository.save(event);
        return toResponse(event);
    }

    @Override
    @Transactional
    public EventResponse setPublished(Long id, boolean published) {
        FestivalEvent event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        event.setPublished(published);
        event = eventRepository.save(event);
        return toResponse(event);
    }

    @Override
    @Transactional
    public EventResponse setCompleted(Long id, boolean completed) {
        FestivalEvent event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        event.setCompleted(completed);
        event = eventRepository.save(event);
        return toResponse(event);
    }

    @Override
    @Transactional
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event", "id", id);
        }
        eventRepository.deleteById(id);
    }

    private void validateTimes(java.time.LocalTime startTime, java.time.LocalTime endTime) {
        if (endTime != null && endTime.isBefore(startTime)) {
            throw new BadRequestException("End time cannot be before start time");
        }
    }

    private EventResponse toResponse(FestivalEvent event) {
        return new EventResponse(
                event.getId(),
                event.getFestival().getId(),
                event.getTitle(),
                event.getDescription(),
                event.getCategory(),
                event.getEventDate(),
                event.getStartTime(),
                event.getEndTime(),
                event.getLocation(),
                event.getImageUrl(),
                event.isPublished(),
                event.isCompleted()
        );
    }
}