package com.vinayakachaviti.video.dto;

import com.vinayakachaviti.common.enums.VideoCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponse {
    private Long id;
    private String title;
    private String description;
    private String youtubeUrl;
    private String thumbnailUrl;
    private VideoCategory category;
    private boolean published;
    private OffsetDateTime publishedAt;
}
