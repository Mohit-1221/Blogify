package com.example.Blogify.Dto.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PostResponse {
    List<PostResponseDto> content;
    int pageNumber;
    int pageSize;
    long totalElements;
    int totalPage;
    boolean lastPage;
}
