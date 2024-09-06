package com.example.Blogify.Transformer;

import com.example.Blogify.Dto.RequestDto.PostRequestDto;
import com.example.Blogify.Dto.ResponseDto.CategoryResponseDto;
import com.example.Blogify.Dto.ResponseDto.CommentResponseDto;
import com.example.Blogify.Dto.ResponseDto.PostResponseDto;
import com.example.Blogify.Dto.ResponseDto.UserResponseDto;
import com.example.Blogify.Entity.Category;
import com.example.Blogify.Entity.Comment;
import com.example.Blogify.Entity.Post;
import com.example.Blogify.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class PostTransformer {
    public static Post preparePost(PostRequestDto postRequestDto){
        return Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .imageName(postRequestDto.getImageName())
                .build();
    }

    public static PostResponseDto prepareResponseDto(Post savedPost) {
        return PostResponseDto.builder()
                .postId(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .date(savedPost.getDateOfPost())
                .imageName(savedPost.getImageName())
                .categories(CategoryTransformer.prepareResponseDto(savedPost.getCategory()))
                .users(UserTransformer.userToUserResponseDto(savedPost.getUser()))
                .build();
    }

    public static PostRequestDto prepareRequestDto(PostResponseDto postResponseDto,String fileName) {
        return PostRequestDto.builder()
                .content(postResponseDto.getContent())
                .imageName(fileName)
                .title(postResponseDto.getTitle())
                .build();
    }
}
