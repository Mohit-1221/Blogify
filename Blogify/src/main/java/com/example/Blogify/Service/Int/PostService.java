package com.example.Blogify.Service.Int;

import com.example.Blogify.Dto.RequestDto.PostRequestDto;
import com.example.Blogify.Dto.ResponseDto.PostResponse;
import com.example.Blogify.Dto.ResponseDto.PostResponseDto;
import com.example.Blogify.Entity.Category;
import com.example.Blogify.Entity.Post;
import com.example.Blogify.Entity.User;
import com.example.Blogify.Exceptions.CategoryNotFound;
import com.example.Blogify.Exceptions.PostNotFound;
import com.example.Blogify.Exceptions.UserNotFoundException;

import java.util.List;

public interface PostService {

    PostResponseDto addPost(PostRequestDto postRequestDto,int userId,int categoryId) throws UserNotFoundException, CategoryNotFound;

    PostResponseDto updatePost(PostRequestDto postRequestDto, int postId) throws PostNotFound;

    void deletePost(int postId) throws PostNotFound;

    PostResponseDto getPostById(int postId) throws PostNotFound;

    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    List<PostResponseDto> getPostByCategory(int categoryId) throws CategoryNotFound;

    List<PostResponseDto> getPostByUser(int userId) throws UserNotFoundException;

    List<PostResponseDto> searchPost(String title);
}
