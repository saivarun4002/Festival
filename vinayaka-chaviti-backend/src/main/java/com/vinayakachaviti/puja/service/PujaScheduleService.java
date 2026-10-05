package com.vinayakachaviti.puja.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.puja.dto.PujaScheduleRequest;
import com.vinayakachaviti.puja.dto.PujaScheduleResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PujaScheduleService {
    PujaScheduleResponse createSchedule(PujaScheduleRequest request);
    PageResponse<PujaScheduleResponse> getPublishedSchedules(Pageable pageable);
    List<PujaScheduleResponse> getTodaysSchedule();
    PageResponse<PujaScheduleResponse> getAllSchedules(Pageable pageable);
    PujaScheduleResponse updateSchedule(Long id, PujaScheduleRequest request);
    PujaScheduleResponse setPublished(Long id, boolean published);
    void deleteSchedule(Long id);
}