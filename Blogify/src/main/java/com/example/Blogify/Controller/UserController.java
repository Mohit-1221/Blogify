package com.example.Blogify.Controller;

import com.example.Blogify.Dto.RequestDto.UserRequestDto;
import com.example.Blogify.Dto.ResponseDto.UserResponseDto;
import com.example.Blogify.Exceptions.UserNotFoundException;
import com.example.Blogify.Service.Int.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = userService.addUser(userRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity updateUserById(@RequestBody UserRequestDto userRequestDto,@PathVariable("userId") int id){
        try {
            UserResponseDto userResponseDto = userService.updateUserById(userRequestDto,id);
            return new ResponseEntity(userResponseDto,HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") int id){
        try {
            userService.deleteUser(id);
            return new ResponseEntity("Deleted Successfully!!!",HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity getUserById(@PathVariable("userId") int id){
        try {
            UserResponseDto userResponseDto = userService.getUserById(id);
            return new ResponseEntity<>(userResponseDto,HttpStatus.CREATED);
        }
        catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllUserById(){
        List<UserResponseDto> userResponseDtoList = userService.getAllUserById();
        return new ResponseEntity<>(userResponseDtoList,HttpStatus.CREATED);
    }
}
