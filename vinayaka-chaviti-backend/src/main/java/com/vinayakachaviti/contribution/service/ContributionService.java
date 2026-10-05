package com.vinayakachaviti.contribution.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.ContributionCategory;
import com.vinayakachaviti.contribution.dto.ContributionRequest;
import com.vinayakachaviti.contribution.dto.ContributionResponse;
import org.springframework.data.domain.Pageable;

public interface ContributionService {
    ContributionResponse create(ContributionRequest request);
    ContributionResponse update(Long id, ContributionRequest request);
    void delete(Long id);
    PageResponse<ContributionResponse> getByCategory(ContributionCategory category, String search, Pageable pageable);
    PageResponse<ContributionResponse> getAll(Pageable pageable);
}
