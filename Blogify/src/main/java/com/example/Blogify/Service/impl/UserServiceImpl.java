package com.example.Blogify.Service.impl;

import com.example.Blogify.Dto.RequestDto.UserRequestDto;
import com.example.Blogify.Dto.ResponseDto.UserResponseDto;
import com.example.Blogify.Entity.User;
import com.example.Blogify.Exceptions.UserNotFoundException;
import com.example.Blogify.Repository.UserRepository;
import com.example.Blogify.Service.Int.UserService;
import com.example.Blogify.Transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User user = UserTransformer.userRequestDtoToUser(userRequestDto);
        User savedUser = userRepository.save(user);
        return UserTransformer.userToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto updateUserById(UserRequestDto userRequestDto, int userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User doesn't Exists!!");
        }
        User user = optionalUser.get();
        user.setName(userRequestDto.getName());
        user.setPassword(userRequestDto.getPassword());
        user.setAbout(userRequestDto.getAbout());
        user.setEmailId(userRequestDto.getEmailId());
        user.setGender(userRequestDto.getGender());

        User savedUser = userRepository.save(user);

        return UserTransformer.userToUserResponseDto(savedUser);
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User doesn't Exists!!");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponseDto getUserById(int id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User doesn't Exists!!");
        }
        return UserTransformer.userToUserResponseDto(optionalUser.get());
    }

    @Override
    public List<UserResponseDto> getAllUserById() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for(User user : users){
            userResponseDtoList.add(UserTransformer.userToUserResponseDto(user));
        }
        return userResponseDtoList;
    }
}
