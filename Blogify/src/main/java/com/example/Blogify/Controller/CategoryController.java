package com.example.Blogify.Controller;

import com.example.Blogify.Dto.RequestDto.CategoryRequestDto;
import com.example.Blogify.Dto.ResponseDto.CategoryResponseDto;
import com.example.Blogify.Exceptions.CategoryNotFound;
import com.example.Blogify.Service.Int.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        CategoryResponseDto categoryResponseDto = categoryService.addCategory(categoryRequestDto);
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.CREATED);
    }
    @PutMapping("/update/{categoryId}")
    public ResponseEntity updateCategory(@RequestBody CategoryRequestDto categoryRequestDto,@PathVariable("categoryId") int categoryId)  {
        try {
            CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryRequestDto,categoryId);
            return new ResponseEntity<>(categoryResponseDto,HttpStatus.CREATED);
        } catch (CategoryNotFound e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable("categoryId") int categoryId)  {
        try {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity("Deleted Successfully!!",HttpStatus.CREATED);
        } catch (CategoryNotFound e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity getCategoryById(@PathVariable("categoryId") int categoryId) {
        try {
            CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(categoryId);
            return new ResponseEntity<>(categoryResponseDto,HttpStatus.CREATED);
        }
        catch (CategoryNotFound e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllCategoryById(){
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getAllCategoryById();
        return new ResponseEntity(categoryResponseDtoList,HttpStatus.CREATED);
    }

}
