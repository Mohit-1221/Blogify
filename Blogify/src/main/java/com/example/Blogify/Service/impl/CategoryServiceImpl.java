package com.example.Blogify.Service.impl;

import com.example.Blogify.Dto.RequestDto.CategoryRequestDto;
import com.example.Blogify.Dto.ResponseDto.CategoryResponseDto;
import com.example.Blogify.Entity.Category;
import com.example.Blogify.Exceptions.CategoryNotFound;
import com.example.Blogify.Repository.CategoryRepository;
import com.example.Blogify.Service.Int.CategoryService;
import com.example.Blogify.Transformer.CategoryTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        Category category = CategoryTransformer.prepareCategory(categoryRequestDto);
        Category savedCategory = categoryRepository.save(category);
        return CategoryTransformer.prepareResponseDto(savedCategory);
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto, int categoryId) throws CategoryNotFound {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(!optionalCategory.isPresent()){
            throw new CategoryNotFound("Category Not Found!!");
        }
        Category category = optionalCategory.get();
        category.setTitle(categoryRequestDto.getTitle());
        category.setDescription(categoryRequestDto.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return CategoryTransformer.prepareResponseDto(savedCategory);
    }

    @Override
    public void deleteCategory(int categoryId) throws CategoryNotFound {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(!optionalCategory.isPresent()){
            throw new CategoryNotFound("Category Not Found!!");
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryResponseDto getCategoryById(int categoryId) throws CategoryNotFound {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(!optionalCategory.isPresent()){
            throw new CategoryNotFound("Category Not Found!!");
        }
        Category category = optionalCategory.get();
        return CategoryTransformer.prepareResponseDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategoryById() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for(Category category : categories){
            categoryResponseDtoList.add(CategoryTransformer.prepareResponseDto(category));
        }
        return categoryResponseDtoList;
    }
}
