package com.vinayakachaviti.family.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.family.dto.FamilyRequest;
import com.vinayakachaviti.family.dto.FamilyResponse;
import org.springframework.data.domain.Pageable;

public interface FamilyService {
    FamilyResponse createFamily(FamilyRequest request);
    PageResponse<FamilyResponse> getPublishedFamilies(Pageable pageable);
    PageResponse<FamilyResponse> getAllFamilies(Pageable pageable);
    long getPublishedFamilyCount();
    FamilyResponse updateFamily(Long id, FamilyRequest request);
    void deleteFamily(Long id);
}