package com.vinayakachaviti.gallery.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * US-GALLERY: A photo album (e.g. "Ganesh Sthapana", "Cultural Programs").
 */
@Entity
@Table(name = "gallery_albums", indexes = {
    @Index(name = "idx_gallery_albums_published", columnList = "published")
})
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class GalleryAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(name = "published", nullable = false)
    private boolean published;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<GalleryImage> images = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    @Builder
    public GalleryAlbum(String title, String coverImageUrl, boolean published) {
        this.title = title;
        this.coverImageUrl = coverImageUrl;
        this.published = published;
    }
}
