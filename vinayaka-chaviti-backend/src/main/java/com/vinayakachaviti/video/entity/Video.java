package com.vinayakachaviti.video.entity;

import com.vinayakachaviti.common.enums.VideoCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

/**
 * US-VIDEOS: A single video entry (YouTube link + metadata) for the video hub.
 */
@Entity
@Table(name = "videos", indexes = {
    @Index(name = "idx_videos_category", columnList = "category"),
    @Index(name = "idx_videos_published", columnList = "published")
})
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "youtube_url", nullable = false, length = 500)
    private String youtubeUrl;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "category", nullable = false, length = 30)
    private VideoCategory category;

    @Column(name = "published", nullable = false)
    private boolean published;

    @Column(name = "published_at")
    private OffsetDateTime publishedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.published && this.publishedAt == null) {
            this.publishedAt = now;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    @Builder
    public Video(String title, String description, String youtubeUrl, String thumbnailUrl,
                 VideoCategory category, boolean published, OffsetDateTime publishedAt) {
        this.title = title;
        this.description = description;
        this.youtubeUrl = youtubeUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
        this.published = published;
        this.publishedAt = publishedAt;
    }
}
