package com.example.Blogify.Transformer;

import com.example.Blogify.Dto.RequestDto.UserRequestDto;
import com.example.Blogify.Dto.ResponseDto.UserResponseDto;
import com.example.Blogify.Entity.User;

public class UserTransformer {

    public static User userRequestDtoToUser(UserRequestDto userRequestDto){
        return User.builder()
                .name(userRequestDto.getName())
                .emailId(userRequestDto.getEmailId())
                .gender(userRequestDto.getGender())
                .password(userRequestDto.getPassword())
                .about(userRequestDto.getAbout())
                .build();
    }
    public static UserResponseDto userToUserResponseDto(User user){
        return UserResponseDto.builder()
                .name(user.getName())
                .emailId(user.getEmailId())
                .build();
    }
}
