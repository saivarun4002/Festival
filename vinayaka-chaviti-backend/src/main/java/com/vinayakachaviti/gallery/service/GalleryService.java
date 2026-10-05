package com.vinayakachaviti.gallery.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.gallery.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GalleryService {
    AlbumResponse createAlbum(AlbumRequest request);
    PageResponse<AlbumResponse> getPublishedAlbums(Pageable pageable);
    PageResponse<AlbumResponse> getAllAlbums(Pageable pageable);
    AlbumResponse renameAlbum(Long id, AlbumRequest request);
    AlbumResponse setAlbumPublished(Long id, boolean published);
    void deleteAlbum(Long id);
    List<ImageResponse> getAlbumImages(Long albumId);
    ImageResponse uploadImage(Long albumId, MultipartFile file, String caption);
    ImageResponse updateImageMetadata(Long imageId, ImageMetadataRequest request);
    void reorderImages(Long albumId, ReorderRequest request);
    void deleteImage(Long imageId);
}