package com.example.Blogify.Dto.RequestDto;

import com.example.Blogify.Entity.Category;
import com.example.Blogify.Entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PostRequestDto {

    String title;

    String content;

    String imageName;
}
