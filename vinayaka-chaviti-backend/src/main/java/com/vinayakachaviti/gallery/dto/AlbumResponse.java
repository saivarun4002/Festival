package com.vinayakachaviti.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponse {
    private Long id;
    private String title;
    private String coverImageUrl;
    private boolean published;
    private long imageCount;
}
