package com.vinayakachaviti.video.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.VideoCategory;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import com.vinayakachaviti.video.dto.VideoRequest;
import com.vinayakachaviti.video.dto.VideoResponse;
import com.vinayakachaviti.video.entity.Video;
import com.vinayakachaviti.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Override
    @Transactional
    public VideoResponse createVideo(VideoRequest request) {
        Video video = Video.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .youtubeUrl(request.getYoutubeUrl())
                .thumbnailUrl(request.getThumbnailUrl())
                .category(request.getCategory())
                .published(request.isPublished())
                .publishedAt(request.isPublished() ? OffsetDateTime.now() : null)
                .build();
        video = videoRepository.save(video);
        return toResponse(video);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<VideoResponse> getPublishedVideos(VideoCategory category, Pageable pageable) {
        Page<Video> page = videoRepository.findPublished(category, pageable);
        Page<VideoResponse> responsePage = page.map(video -> toResponse(video));
        PageResponse<VideoResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<VideoResponse> getAllVideos(Pageable pageable) {
        Page<Video> page = videoRepository.findAll(pageable);
        Page<VideoResponse> responsePage = page.map(video -> toResponse(video));
        PageResponse<VideoResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional
    public VideoResponse updateVideo(Long id, VideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", id));

        boolean wasPublished = video.isPublished();
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());
        video.setYoutubeUrl(request.getYoutubeUrl());
        video.setThumbnailUrl(request.getThumbnailUrl());
        video.setCategory(request.getCategory());
        video.setPublished(request.isPublished());
        if (!wasPublished && request.isPublished()) {
            video.setPublishedAt(OffsetDateTime.now());
        }
        video = videoRepository.save(video);
        return toResponse(video);
    }

    @Override
    @Transactional
    public VideoResponse setPublished(Long id, boolean published) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", id));
        video.setPublished(published);
        if (published && video.getPublishedAt() == null) {
            video.setPublishedAt(OffsetDateTime.now());
        }
        video = videoRepository.save(video);
        return toResponse(video);
    }

    @Override
    @Transactional
    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Video", "id", id);
        }
        videoRepository.deleteById(id);
    }

    private VideoResponse toResponse(Video video) {
        return new VideoResponse(
                video.getId(), video.getTitle(), video.getDescription(), video.getYoutubeUrl(),
                video.getThumbnailUrl(), video.getCategory(), video.isPublished(), video.getPublishedAt()
        );
    }
}