package com.vinayakachaviti.announcement.service;

import com.vinayakachaviti.announcement.dto.AnnouncementRequest;
import com.vinayakachaviti.announcement.dto.AnnouncementResponse;
import com.vinayakachaviti.announcement.entity.Announcement;
import com.vinayakachaviti.announcement.repository.AnnouncementRepository;
import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AnnouncementResponse createAnnouncement(AnnouncementRequest request) {
        Announcement announcement = Announcement.builder()
                .title(request.getTitle())
                .message(request.getMessage())
                .priority(request.getPriority())
                .published(request.isPublished())
                .publishedAt(request.isPublished() ? OffsetDateTime.now() : null)
                .build();
        announcement = announcementRepository.save(announcement);
        return toResponse(announcement);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AnnouncementResponse> getPublishedAnnouncements(Pageable pageable) {
        Page<Announcement> page = announcementRepository.findByPublishedTrueOrderByPublishedAtDesc(pageable);
        Page<AnnouncementResponse> responsePage = page.map(announcement -> toResponse(announcement));
        PageResponse<AnnouncementResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AnnouncementResponse> getAllAnnouncements(Pageable pageable) {
        Page<Announcement> page = announcementRepository.findAll(pageable);
        Page<AnnouncementResponse> responsePage = page.map(announcement -> toResponse(announcement));
        PageResponse<AnnouncementResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional
    public AnnouncementResponse updateAnnouncement(Long id, AnnouncementRequest request) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement", "id", id));

        boolean wasPublished = announcement.isPublished();
        announcement.setTitle(request.getTitle());
        announcement.setMessage(request.getMessage());
        announcement.setPriority(request.getPriority());
        announcement.setPublished(request.isPublished());
        if (!wasPublished && request.isPublished()) {
            announcement.setPublishedAt(OffsetDateTime.now());
        }
        announcement = announcementRepository.save(announcement);
        return toResponse(announcement);
    }

    @Override
    @Transactional
    public AnnouncementResponse setPublished(Long id, boolean published) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement", "id", id));
        announcement.setPublished(published);
        if (published && announcement.getPublishedAt() == null) {
            announcement.setPublishedAt(OffsetDateTime.now());
        }
        announcement = announcementRepository.save(announcement);
        return toResponse(announcement);
    }

    @Override
    @Transactional
    public void deleteAnnouncement(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Announcement", "id", id);
        }
        announcementRepository.deleteById(id);
    }

    private AnnouncementResponse toResponse(Announcement announcement) {
        return modelMapper.map(announcement, AnnouncementResponse.class);
    }
}