package com.example.Blogify.Dto.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PostResponseDto {
    int postId;
    String title;
    String content;
    String imageName;
    Date date;
    CategoryResponseDto categories;
    UserResponseDto users;
    List<CommentResponseDto> comments;
}
