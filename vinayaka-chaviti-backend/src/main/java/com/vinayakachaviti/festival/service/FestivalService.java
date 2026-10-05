package com.vinayakachaviti.festival.service;

import com.vinayakachaviti.festival.dto.FestivalCreateRequest;
import com.vinayakachaviti.festival.dto.FestivalResponse;
import com.vinayakachaviti.festival.dto.FestivalUpdateRequest;

public interface FestivalService {
    FestivalResponse createFestival(FestivalCreateRequest request);
    FestivalResponse getFestivalById(Long id);
    FestivalResponse getFestivalByYear(Integer year);
    FestivalResponse getCurrentFestival();
    FestivalResponse updateFestival(Long id, FestivalUpdateRequest request);
    void deleteFestival(Long id);
}