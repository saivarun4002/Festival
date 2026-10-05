package com.vinayakachaviti.puja.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import com.vinayakachaviti.puja.dto.PujaScheduleRequest;
import com.vinayakachaviti.puja.dto.PujaScheduleResponse;
import com.vinayakachaviti.puja.entity.PujaSchedule;
import com.vinayakachaviti.puja.repository.PujaScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * US-PUJA: Business logic for daily/special puja schedule entries.
 */
@Service
@RequiredArgsConstructor
public class PujaScheduleServiceImpl implements PujaScheduleService {

    private final PujaScheduleRepository repository;

    @Override
    @Transactional
    public PujaScheduleResponse createSchedule(PujaScheduleRequest request) {
        PujaSchedule schedule = PujaSchedule.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .pujaType(request.getPujaType())
                .scheduledDate(request.getScheduledDate())
                .scheduledTime(request.getScheduledTime())
                .durationMinutes(request.getDurationMinutes())
                .published(request.isPublished())
                .build();
        schedule = repository.save(schedule);
        return toResponse(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PujaScheduleResponse> getPublishedSchedules(Pageable pageable) {
        Page<PujaSchedule> page = repository.findByPublishedTrueOrderByScheduledDateAscScheduledTimeAsc(pageable);
        Page<PujaScheduleResponse> responsePage = page.map(schedule -> toResponse(schedule));
        PageResponse<PujaScheduleResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PujaScheduleResponse> getTodaysSchedule() {
        return repository.findTodaysSchedule(LocalDate.now()).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PujaScheduleResponse> getAllSchedules(Pageable pageable) {
        Page<PujaSchedule> page = repository.findAllByOrderByScheduledDateAscScheduledTimeAsc(pageable);
        Page<PujaScheduleResponse> responsePage = page.map(schedule -> toResponse(schedule));
        PageResponse<PujaScheduleResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional
    public PujaScheduleResponse updateSchedule(Long id, PujaScheduleRequest request) {
        PujaSchedule schedule = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PujaSchedule", "id", id));
        schedule.setTitle(request.getTitle());
        schedule.setDescription(request.getDescription());
        schedule.setPujaType(request.getPujaType());
        schedule.setScheduledDate(request.getScheduledDate());
        schedule.setScheduledTime(request.getScheduledTime());
        schedule.setDurationMinutes(request.getDurationMinutes());
        schedule.setPublished(request.isPublished());
        schedule = repository.save(schedule);
        return toResponse(schedule);
    }

    @Override
    @Transactional
    public PujaScheduleResponse setPublished(Long id, boolean published) {
        PujaSchedule schedule = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PujaSchedule", "id", id));
        schedule.setPublished(published);
        schedule = repository.save(schedule);
        return toResponse(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("PujaSchedule", "id", id);
        }
        repository.deleteById(id);
    }

    private PujaScheduleResponse toResponse(PujaSchedule schedule) {
        return new PujaScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getPujaType(),
                schedule.getScheduledDate(),
                schedule.getScheduledTime(),
                schedule.getDurationMinutes(),
                schedule.isPublished()
        );
    }
}