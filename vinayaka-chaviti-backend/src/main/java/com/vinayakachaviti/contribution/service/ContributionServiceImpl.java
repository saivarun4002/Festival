package com.vinayakachaviti.contribution.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.ContributionCategory;
import com.vinayakachaviti.contribution.dto.ContributionRequest;
import com.vinayakachaviti.contribution.dto.ContributionResponse;
import com.vinayakachaviti.contribution.entity.Contribution;
import com.vinayakachaviti.contribution.repository.ContributionRepository;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ContributionServiceImpl implements ContributionService {

    private final ContributionRepository repository;

    @Override
    @Transactional
    public ContributionResponse create(ContributionRequest request) {
        Contribution contribution = Contribution.builder()
                .category(request.getCategory())
                .eventLabel(request.getEventLabel())
                .itemName(request.getItemName())
                .donorName(request.getDonorName())
                .designation(request.getDesignation())
                .quantity(request.getQuantity())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .build();
        contribution = repository.save(contribution);
        return toResponse(contribution);
    }

    @Override
    @Transactional
    public ContributionResponse update(Long id, ContributionRequest request) {
        Contribution contribution = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contribution", "id", id));
        contribution.setCategory(request.getCategory());
        contribution.setEventLabel(request.getEventLabel());
        contribution.setItemName(request.getItemName());
        contribution.setDonorName(request.getDonorName());
        contribution.setDesignation(request.getDesignation());
        contribution.setQuantity(request.getQuantity());
        if (request.getDisplayOrder() != null) {
            contribution.setDisplayOrder(request.getDisplayOrder());
        }
        contribution = repository.save(contribution);
        return toResponse(contribution);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contribution", "id", id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ContributionResponse> getByCategory(ContributionCategory category, String search, Pageable pageable) {
        Page<Contribution> page = StringUtils.hasText(search)
                ? repository.findByCategoryAndDonorNameContainingIgnoreCaseOrderByDisplayOrderAscIdAsc(category, search, pageable)
                : repository.findByCategoryOrderByDisplayOrderAscIdAsc(category, pageable);
        return PageResponse.from(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ContributionResponse> getAll(Pageable pageable) {
        Page<Contribution> page = repository.findAll(pageable);
        return PageResponse.from(page.map(this::toResponse));
    }

    private ContributionResponse toResponse(Contribution c) {
        ContributionResponse response = new ContributionResponse();
        response.setId(c.getId());
        response.setCategory(c.getCategory());
        response.setEventLabel(c.getEventLabel());
        response.setItemName(c.getItemName());
        response.setDonorName(c.getDonorName());
        response.setDesignation(c.getDesignation());
        response.setQuantity(c.getQuantity());
        response.setDisplayOrder(c.getDisplayOrder());
        response.setCreatedAt(c.getCreatedAt());
        return response;
    }
}
