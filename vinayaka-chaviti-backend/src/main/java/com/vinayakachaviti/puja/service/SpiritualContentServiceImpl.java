package com.vinayakachaviti.puja.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.SpiritualContentCategory;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import com.vinayakachaviti.puja.dto.SpiritualContentRequest;
import com.vinayakachaviti.puja.dto.SpiritualContentResponse;
import com.vinayakachaviti.puja.entity.SpiritualContent;
import com.vinayakachaviti.puja.repository.SpiritualContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SpiritualContentServiceImpl implements SpiritualContentService {

    private final SpiritualContentRepository repository;

    @Override
    @Transactional
    public SpiritualContentResponse createContent(SpiritualContentRequest request) {
        SpiritualContent content = SpiritualContent.builder()
                .title(request.getTitle())
                .category(request.getCategory())
                .content(request.getContent())
                .displayOrder(request.getDisplayOrder())
                .published(request.isPublished())
                .build();
        content = repository.save(content);
        return toResponse(content);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SpiritualContentResponse> getPublishedContent(SpiritualContentCategory category, Pageable pageable) {
        if (category != null) {
            Page<SpiritualContent> categoryPage = repository.findByPublishedTrueAndCategoryOrderByDisplayOrderAsc(category, pageable);
            Page<SpiritualContentResponse> categoryResponsePage = categoryPage.map(content -> toResponse(content));
            PageResponse<SpiritualContentResponse> categoryResult = PageResponse.from(categoryResponsePage);
            return categoryResult;
        }
        Page<SpiritualContent> page = repository.findByPublishedTrueOrderByDisplayOrderAsc(pageable);
        Page<SpiritualContentResponse> responsePage = page.map(content -> toResponse(content));
        PageResponse<SpiritualContentResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SpiritualContentResponse> getAllContent(Pageable pageable) {
        Page<SpiritualContent> page = repository.findAllByOrderByDisplayOrderAsc(pageable);
        Page<SpiritualContentResponse> responsePage = page.map(content -> toResponse(content));
        PageResponse<SpiritualContentResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional
    public SpiritualContentResponse updateContent(Long id, SpiritualContentRequest request) {
        SpiritualContent content = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SpiritualContent", "id", id));
        content.setTitle(request.getTitle());
        content.setCategory(request.getCategory());
        content.setContent(request.getContent());
        content.setDisplayOrder(request.getDisplayOrder());
        content.setPublished(request.isPublished());
        content = repository.save(content);
        return toResponse(content);
    }

    @Override
    @Transactional
    public SpiritualContentResponse setPublished(Long id, boolean published) {
        SpiritualContent content = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SpiritualContent", "id", id));
        content.setPublished(published);
        content = repository.save(content);
        return toResponse(content);
    }

    @Override
    @Transactional
    public void deleteContent(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("SpiritualContent", "id", id);
        }
        repository.deleteById(id);
    }

    private SpiritualContentResponse toResponse(SpiritualContent content) {
        return new SpiritualContentResponse(
                content.getId(),
                content.getTitle(),
                content.getCategory(),
                content.getContent(),
                content.getDisplayOrder(),
                content.isPublished()
        );
    }
}