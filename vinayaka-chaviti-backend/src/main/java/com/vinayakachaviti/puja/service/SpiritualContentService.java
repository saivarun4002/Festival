package com.vinayakachaviti.puja.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.SpiritualContentCategory;
import com.vinayakachaviti.puja.dto.SpiritualContentRequest;
import com.vinayakachaviti.puja.dto.SpiritualContentResponse;
import org.springframework.data.domain.Pageable;

public interface SpiritualContentService {
    SpiritualContentResponse createContent(SpiritualContentRequest request);
    PageResponse<SpiritualContentResponse> getPublishedContent(SpiritualContentCategory category, Pageable pageable);
    PageResponse<SpiritualContentResponse> getAllContent(Pageable pageable);
    SpiritualContentResponse updateContent(Long id, SpiritualContentRequest request);
    SpiritualContentResponse setPublished(Long id, boolean published);
    void deleteContent(Long id);
}