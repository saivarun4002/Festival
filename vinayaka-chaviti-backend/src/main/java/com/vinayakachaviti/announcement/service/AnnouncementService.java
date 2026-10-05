package com.vinayakachaviti.announcement.service;

import com.vinayakachaviti.announcement.dto.AnnouncementRequest;
import com.vinayakachaviti.announcement.dto.AnnouncementResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface AnnouncementService {
    //to create a new announcement
    AnnouncementResponse createAnnouncement(AnnouncementRequest request);

    //to get a paginated list of published announcements
    PageResponse<AnnouncementResponse> getPublishedAnnouncements(Pageable pageable);

    //to get a paginated list of all announcements (including unpublished)
    PageResponse<AnnouncementResponse> getAllAnnouncements(Pageable pageable);

    //to update an existing announcement
    AnnouncementResponse updateAnnouncement(Long id, AnnouncementRequest request);

    //to set the status of an announcement to published
    AnnouncementResponse setPublished(Long id, boolean published);

    //to delete an announcement by its ID
    void deleteAnnouncement(Long id);
}