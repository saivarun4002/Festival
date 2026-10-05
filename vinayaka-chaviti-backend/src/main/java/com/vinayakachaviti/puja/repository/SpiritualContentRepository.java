package com.vinayakachaviti.puja.repository;

import com.vinayakachaviti.common.enums.SpiritualContentCategory;
import com.vinayakachaviti.puja.entity.SpiritualContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpiritualContentRepository extends JpaRepository<SpiritualContent, Long> {

    Page<SpiritualContent> findByPublishedTrueAndCategoryOrderByDisplayOrderAsc(SpiritualContentCategory category, Pageable pageable);

    Page<SpiritualContent> findByPublishedTrueOrderByDisplayOrderAsc(Pageable pageable);

    Page<SpiritualContent> findAllByOrderByDisplayOrderAsc(Pageable pageable);
}
