package com.vinayakachaviti.gallery.repository;

import com.vinayakachaviti.gallery.entity.GalleryAlbum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryAlbumRepository extends JpaRepository<GalleryAlbum, Long> {
    Page<GalleryAlbum> findByPublishedTrue(Pageable pageable);
}
