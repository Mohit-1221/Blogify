package com.example.Blogify.Service.Int;

import com.example.Blogify.Dto.RequestDto.CategoryRequestDto;
import com.example.Blogify.Dto.ResponseDto.CategoryResponseDto;
import com.example.Blogify.Exceptions.CategoryNotFound;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto,int categoryId) throws CategoryNotFound;

    void deleteCategory(int categoryId) throws CategoryNotFound;

    CategoryResponseDto getCategoryById(int categoryId) throws CategoryNotFound;

    List<CategoryResponseDto> getAllCategoryById();
}
