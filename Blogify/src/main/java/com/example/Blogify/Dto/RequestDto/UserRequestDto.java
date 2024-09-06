package com.example.Blogify.Dto.RequestDto;

import com.example.Blogify.Enum.Gender;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRequestDto {
    String name;

    String emailId;

    Gender gender;

    String password;

    String about;
}
