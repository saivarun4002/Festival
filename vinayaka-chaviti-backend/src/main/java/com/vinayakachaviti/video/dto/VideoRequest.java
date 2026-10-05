package com.vinayakachaviti.video.dto;

import com.vinayakachaviti.common.enums.VideoCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * US-VIDEOS, PRD: Validates the YouTube URL format at the API boundary as required
 * by the admin video management rules ("Validate YouTube URLs").
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "YouTube URL is required")
    @Pattern(
            regexp = "^(https?://)?(www\\.)?(youtube\\.com/(watch\\?v=|embed/|shorts/)|youtu\\.be/)[\\w-]+(\\S*)?$",
            message = "Must be a valid YouTube URL"
    )
    private String youtubeUrl;

    private String thumbnailUrl;

    @NotNull(message = "Category is required")
    private VideoCategory category;

    private boolean published;
}
