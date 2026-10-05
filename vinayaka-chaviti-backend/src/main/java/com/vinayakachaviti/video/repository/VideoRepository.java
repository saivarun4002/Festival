package com.vinayakachaviti.video.repository;

import com.vinayakachaviti.common.enums.VideoCategory;
import com.vinayakachaviti.video.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("""
            SELECT v FROM Video v
            WHERE v.published = true
              AND (:category IS NULL OR v.category = :category)
            ORDER BY v.publishedAt DESC
            """)
    Page<Video> findPublished(@Param("category") VideoCategory category, Pageable pageable);
}
