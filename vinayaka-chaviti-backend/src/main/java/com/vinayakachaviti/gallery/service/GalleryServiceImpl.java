package com.vinayakachaviti.gallery.service;

import com.vinayakachaviti.common.dto.PageResponse;
import com.vinayakachaviti.exception.ResourceNotFoundException;
import com.vinayakachaviti.gallery.dto.*;
import com.vinayakachaviti.gallery.entity.GalleryAlbum;
import com.vinayakachaviti.gallery.entity.GalleryImage;
import com.vinayakachaviti.gallery.repository.GalleryAlbumRepository;
import com.vinayakachaviti.gallery.repository.GalleryImageRepository;
import com.vinayakachaviti.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * US-GALLERY: Album + image management. Image bytes are delegated to
 * StorageService (drag-and-drop upload UI on the frontend posts multipart
 * files here); only URLs/captions/order are persisted in MySQL.
 */
@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryAlbumRepository albumRepository;
    private final GalleryImageRepository imageRepository;
    private final StorageService storageService;

    @Override
    @Transactional
    public AlbumResponse createAlbum(AlbumRequest request) {
        GalleryAlbum album = GalleryAlbum.builder()
                .title(request.getTitle())
                .coverImageUrl(request.getCoverImageUrl())
                .published(request.isPublished())
                .build();
        album = albumRepository.save(album);
        return toAlbumResponse(album);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AlbumResponse> getPublishedAlbums(Pageable pageable) {
        Page<GalleryAlbum> page = albumRepository.findByPublishedTrue(pageable);
        Page<AlbumResponse> responsePage = page.map(album -> toAlbumResponse(album));
        PageResponse<AlbumResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AlbumResponse> getAllAlbums(Pageable pageable) {
        Page<GalleryAlbum> page = albumRepository.findAll(pageable);
        Page<AlbumResponse> responsePage = page.map(album -> toAlbumResponse(album));
        PageResponse<AlbumResponse> result = PageResponse.from(responsePage);
        return result;
    }

    @Override
    @Transactional
    public AlbumResponse renameAlbum(Long id, AlbumRequest request) {
        GalleryAlbum album = getAlbumOrThrow(id);
        album.setTitle(request.getTitle());
        album.setCoverImageUrl(request.getCoverImageUrl());
        album.setPublished(request.isPublished());
        album = albumRepository.save(album);
        return toAlbumResponse(album);
    }

    @Override
    @Transactional
    public AlbumResponse setAlbumPublished(Long id, boolean published) {
        GalleryAlbum album = getAlbumOrThrow(id);
        album.setPublished(published);
        album = albumRepository.save(album);
        return toAlbumResponse(album);
    }

    @Override
    @Transactional
    public void deleteAlbum(Long id) {
        GalleryAlbum album = getAlbumOrThrow(id);
        album.getImages().forEach(image -> storageService.delete(image.getImageUrl()));
        albumRepository.delete(album);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getAlbumImages(Long albumId) {
        getAlbumOrThrow(albumId);
        return imageRepository.findByAlbumIdOrderByDisplayOrderAsc(albumId)
                .stream().map(this::toImageResponse).toList();
    }

    @Override
    @Transactional
    public ImageResponse uploadImage(Long albumId, MultipartFile file, String caption) {
        GalleryAlbum album = getAlbumOrThrow(albumId);
        try {
            long nextOrder = imageRepository.countByAlbumId(albumId);
            String url = storageService.store(
                    "gallery/" + albumId, file.getOriginalFilename(), file.getBytes(), file.getContentType()
            );
            GalleryImage image = GalleryImage.builder()
                    .album(album)
                    .imageUrl(url)
                    .caption(caption)
                    .displayOrder((int) nextOrder)
                    .build();
            image = imageRepository.save(image);

            if (album.getCoverImageUrl() == null) {
                album.setCoverImageUrl(url);
                albumRepository.save(album);
            }

            return toImageResponse(image);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read uploaded file: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public ImageResponse updateImageMetadata(Long imageId, ImageMetadataRequest request) {
        GalleryImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery image", "id", imageId));
        image.setCaption(request.getCaption());
        image.setDisplayOrder(request.getDisplayOrder());
        image = imageRepository.save(image);
        return toImageResponse(image);
    }

    @Override
    @Transactional
    public void reorderImages(Long albumId, ReorderRequest request) {
        getAlbumOrThrow(albumId);
        List<GalleryImage> images = imageRepository.findByAlbumIdOrderByDisplayOrderAsc(albumId);
        Map<Long, GalleryImage> byId = images.stream()
                .collect(java.util.stream.Collectors.toMap(GalleryImage::getId, img -> img));

        int order = 0;
        for (Long imageId : request.getImageIdsInOrder()) {
            GalleryImage image = byId.get(imageId);
            if (image != null) {
                image.setDisplayOrder(order++);
                imageRepository.save(image);
            }
        }
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        GalleryImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery image", "id", imageId));
        storageService.delete(image.getImageUrl());
        imageRepository.delete(image);
    }

    private GalleryAlbum getAlbumOrThrow(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery album", "id", id));
    }

    private AlbumResponse toAlbumResponse(GalleryAlbum album) {
        long count = imageRepository.countByAlbumId(album.getId());
        return new AlbumResponse(album.getId(), album.getTitle(), album.getCoverImageUrl(), album.isPublished(), count);
    }

    private ImageResponse toImageResponse(GalleryImage image) {
        return new ImageResponse(image.getId(), image.getAlbum().getId(), image.getImageUrl(), image.getCaption(), image.getDisplayOrder());
    }
}