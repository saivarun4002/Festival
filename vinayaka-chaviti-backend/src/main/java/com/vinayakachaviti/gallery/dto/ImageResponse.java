package com.vinayakachaviti.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {
    private Long id;
    private Long albumId;
    private String imageUrl;
    private String caption;
    private int displayOrder;
}
