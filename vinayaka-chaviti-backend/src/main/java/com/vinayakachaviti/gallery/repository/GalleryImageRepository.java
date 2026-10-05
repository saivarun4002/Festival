package com.vinayakachaviti.gallery.repository;

import com.vinayakachaviti.gallery.entity.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {
    List<GalleryImage> findByAlbumIdOrderByDisplayOrderAsc(Long albumId);
    long countByAlbumId(Long albumId);
}
