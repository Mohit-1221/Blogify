package com.example.Blogify.Service.Int;

import com.example.Blogify.Dto.RequestDto.CommentRequestDto;
import com.example.Blogify.Dto.ResponseDto.CommentResponseDto;
import com.example.Blogify.Exceptions.CommentNotFound;
import com.example.Blogify.Exceptions.PostNotFound;
import com.example.Blogify.Exceptions.UserNotFoundException;

public interface CommentService {
    CommentResponseDto addComment(CommentRequestDto commentRequestDto,int userId,int postId) throws UserNotFoundException, PostNotFound;

    void deleteComment(int commentId) throws CommentNotFound;
}
