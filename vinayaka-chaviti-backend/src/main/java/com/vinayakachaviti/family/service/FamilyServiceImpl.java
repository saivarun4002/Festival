package com.vinayakachaviti.family.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import com.vinayakachaviti.family.dto.FamilyRequest;
import com.vinayakachaviti.family.dto.FamilyResponse;
import com.vinayakachaviti.family.entity.Family;
import com.vinayakachaviti.family.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;

    @Override
    @Transactional
    public FamilyResponse createFamily(FamilyRequest request) {
        Family family = Family.builder()
                .familyName(request.getFamilyName())
                .representativeName(request.getRepresentativeName())
                .published(request.isPublished())
                .build();
        family = familyRepository.save(family);
        return toResponse(family);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FamilyResponse> getPublishedFamilies(Pageable pageable) {
        Page<Family> page = familyRepository.findByPublishedTrue(pageable);
        Page<FamilyResponse> responsePage = page.map(family -> toResponse(family));
        PageResponse<FamilyResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FamilyResponse> getAllFamilies(Pageable pageable) {
        Page<Family> page = familyRepository.findAll(pageable);
        Page<FamilyResponse> responsePage = page.map(family -> toResponse(family));
        PageResponse<FamilyResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public long getPublishedFamilyCount() {
        return familyRepository.countByPublishedTrue();
    }

    @Override
    @Transactional
    public FamilyResponse updateFamily(Long id, FamilyRequest request) {
        Family family = familyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family", "id", id));
        family.setFamilyName(request.getFamilyName());
        family.setRepresentativeName(request.getRepresentativeName());
        family.setPublished(request.isPublished());
        family = familyRepository.save(family);
        return toResponse(family);
    }

    @Override
    @Transactional
    public void deleteFamily(Long id) {
        if (!familyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Family", "id", id);
        }
        familyRepository.deleteById(id);
    }

    private FamilyResponse toResponse(Family family) {
        return new FamilyResponse(family.getId(), family.getFamilyName(), family.getRepresentativeName(), family.isPublished());
    }
}