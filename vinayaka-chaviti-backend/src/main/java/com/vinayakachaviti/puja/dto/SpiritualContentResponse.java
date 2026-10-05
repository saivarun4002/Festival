package com.vinayakachaviti.puja.dto;

import com.vinayakachaviti.common.enums.SpiritualContentCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpiritualContentResponse {
    private Long id;
    private String title;
    private SpiritualContentCategory category;
    private String content;
    private int displayOrder;
    private boolean published;
}
