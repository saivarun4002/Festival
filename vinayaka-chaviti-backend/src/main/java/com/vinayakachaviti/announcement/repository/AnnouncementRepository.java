package com.vinayakachaviti.announcement.repository;

import com.vinayakachaviti.announcement.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    // Find all published announcements, ordered by publishedAt descending (most recent first)
    Page<Announcement> findByPublishedTrueOrderByPublishedAtDesc(Pageable pageable);
}
