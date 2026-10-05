package com.vinayakachaviti.festival.service;

import com.vinayakachaviti.festival.dto.FestivalCreateRequest;
import com.vinayakachaviti.festival.dto.FestivalResponse;
import com.vinayakachaviti.festival.dto.FestivalUpdateRequest;
import com.vinayakachaviti.festival.entity.Festival;
import com.vinayakachaviti.festival.repository.FestivalRepository;
import com.vinayakachaviti.exception.BadRequestException;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import com.vinayakachaviti.common.enums.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FestivalServiceImpl implements FestivalService {
    private final FestivalRepository festivalRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public FestivalResponse createFestival(FestivalCreateRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("End date cannot be before start date");
        }
        if (festivalRepository.existsByYear(request.getYear())) {
            throw new BadRequestException("Festival already exists for year " + request.getYear());
        }
        Festival festival = modelMapper.map(request, Festival.class);
        festival.setStatus(Status.UPCOMING);
        festival = festivalRepository.save(festival);
        return modelMapper.map(festival, FestivalResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public FestivalResponse getFestivalById(Long id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Festival", "id", id));
        return modelMapper.map(festival, FestivalResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public FestivalResponse getFestivalByYear(Integer year) {
        Festival festival = festivalRepository.findByYear(year)
                .orElseThrow(() -> new ResourceNotFoundException("Festival", "year", year));
        return modelMapper.map(festival, FestivalResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public FestivalResponse getCurrentFestival() {
        Festival festival = festivalRepository.findCurrentFestival()
                .orElseThrow(() -> new ResourceNotFoundException("No current festival found"));
        return modelMapper.map(festival, FestivalResponse.class);
    }

    @Override
    @Transactional
    public FestivalResponse updateFestival(Long id, FestivalUpdateRequest request) {
        Festival existing = festivalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Festival", "id", id));
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("End date cannot be before start date");
        }
        if (request.getYear() != null && !request.getYear().equals(existing.getYear()) && festivalRepository.existsByYear(request.getYear())) {
            throw new BadRequestException("Festival already exists for year " + request.getYear());
        }
        existing.setName(request.getName());
        existing.setYear(request.getYear());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        existing.setLocation(request.getLocation());
        existing.setDescription(request.getDescription());
        existing.setStatus(request.getStatus());
        existing = festivalRepository.save(existing);
        return modelMapper.map(existing, FestivalResponse.class);
    }

    @Override
    @Transactional
    public void deleteFestival(Long id) {
        if (!festivalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Festival", "id", id);
        }
        festivalRepository.deleteById(id);
    }
}