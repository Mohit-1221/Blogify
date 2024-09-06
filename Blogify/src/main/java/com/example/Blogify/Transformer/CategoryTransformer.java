package com.example.Blogify.Transformer;

import com.example.Blogify.Dto.RequestDto.CategoryRequestDto;
import com.example.Blogify.Dto.ResponseDto.CategoryResponseDto;
import com.example.Blogify.Entity.Category;

public class CategoryTransformer {
    public static Category prepareCategory(CategoryRequestDto categoryRequestDto){
        return Category.builder()
                .title(categoryRequestDto.getTitle())
                .description(categoryRequestDto.getDescription())
                .build();
    }

    public static CategoryResponseDto prepareResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }
}
