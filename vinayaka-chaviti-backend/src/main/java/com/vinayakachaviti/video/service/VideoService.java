package com.vinayakachaviti.video.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.common.enums.VideoCategory;
import com.vinayakachaviti.video.dto.VideoRequest;
import com.vinayakachaviti.video.dto.VideoResponse;
import org.springframework.data.domain.Pageable;

public interface VideoService {
    VideoResponse createVideo(VideoRequest request);
    PageResponse<VideoResponse> getPublishedVideos(VideoCategory category, Pageable pageable);
    PageResponse<VideoResponse> getAllVideos(Pageable pageable);
    VideoResponse updateVideo(Long id, VideoRequest request);
    VideoResponse setPublished(Long id, boolean published);
    void deleteVideo(Long id);
}