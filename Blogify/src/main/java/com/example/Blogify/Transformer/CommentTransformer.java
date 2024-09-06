package com.example.Blogify.Transformer;

import com.example.Blogify.Dto.RequestDto.CommentRequestDto;
import com.example.Blogify.Dto.ResponseDto.CommentResponseDto;
import com.example.Blogify.Dto.ResponseDto.PostResponse;
import com.example.Blogify.Dto.ResponseDto.PostResponseDto;
import com.example.Blogify.Entity.Comment;
import com.example.Blogify.Entity.Post;
import com.example.Blogify.Entity.User;

public class CommentTransformer {
    public static Comment prepareComment(CommentRequestDto commentRequestDto, User user, Post post) {
        return Comment.builder()
                .content(commentRequestDto.getContent())
                .post(post)
                .user(user)
                .build();
    }

    public static CommentResponseDto prepareResponseDto(Comment savedComment) {
        return CommentResponseDto.builder()
                .content(savedComment.getContent())
                .commentId(savedComment.getId())
                .post(PostTransformer.prepareResponseDto(savedComment.getPost()))
                .user(UserTransformer.userToUserResponseDto(savedComment.getUser()))
                .build();
    }
}
