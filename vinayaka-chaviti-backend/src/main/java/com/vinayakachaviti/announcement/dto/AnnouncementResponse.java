package com.vinayakachaviti.announcement.dto;

import com.vinayakachaviti.common.enums.AnnouncementPriority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementResponse {
    private Long id;
    private String title;
    private String message;
    private AnnouncementPriority priority;
    private boolean published;
    private OffsetDateTime publishedAt;
}
