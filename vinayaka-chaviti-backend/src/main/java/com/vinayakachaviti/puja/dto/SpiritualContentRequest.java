package com.vinayakachaviti.puja.dto;

import com.vinayakachaviti.common.enums.SpiritualContentCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpiritualContentRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Category is required")
    private SpiritualContentCategory category;

    @NotBlank(message = "Content is required")
    private String content;

    private int displayOrder;

    private boolean published;
}
