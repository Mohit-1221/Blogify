package com.example.Blogify.Service.Int;

import com.example.Blogify.Dto.RequestDto.UserRequestDto;
import com.example.Blogify.Dto.ResponseDto.UserResponseDto;
import com.example.Blogify.Exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    UserResponseDto addUser(UserRequestDto userRequestDto);

    UserResponseDto updateUserById(UserRequestDto userRequestDto, int userId) throws UserNotFoundException;

    void deleteUser(int userId) throws UserNotFoundException;

    UserResponseDto getUserById(int id) throws UserNotFoundException;

    List<UserResponseDto> getAllUserById();
}
