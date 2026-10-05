package com.vinayakachaviti.gallery.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

/**
 * US-GALLERY: A single image belonging to a GalleryAlbum. Only the URL/metadata
 * is stored here — the actual bytes live wherever StorageService put them.
 */
@Entity
@Table(name = "gallery_images", indexes = {
    @Index(name = "idx_gallery_images_album_id", columnList = "album_id")
})
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class GalleryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "album_id", nullable = false, foreignKey = @ForeignKey(name = "fk_gallery_images_album"))
    private GalleryAlbum album;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "caption", length = 255)
    private String caption;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    @Builder
    public GalleryImage(GalleryAlbum album, String imageUrl, String caption, int displayOrder) {
        this.album = album;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.displayOrder = displayOrder;
    }
}
