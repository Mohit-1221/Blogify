package com.example.Blogify.Dto.ResponseDto;

import com.example.Blogify.Entity.Post;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CommentResponseDto {
    int commentId;
    String content;
    UserResponseDto user;
    PostResponseDto post;
}
