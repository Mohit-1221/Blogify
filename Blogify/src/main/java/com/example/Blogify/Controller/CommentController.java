package com.example.Blogify.Controller;

import com.example.Blogify.Dto.RequestDto.CommentRequestDto;
import com.example.Blogify.Dto.ResponseDto.CommentResponseDto;
import com.example.Blogify.Exceptions.CommentNotFound;
import com.example.Blogify.Exceptions.PostNotFound;
import com.example.Blogify.Exceptions.UserNotFoundException;
import com.example.Blogify.Service.Int.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;
    @PostMapping("/add/user/{userId}/post/{postId}")
    public ResponseEntity addComment(@RequestBody CommentRequestDto commentRequestDto,
                                     @PathVariable("userId") int userId,
                                     @PathVariable("postId") int postId)  {
        try {
            CommentResponseDto commentResponseDto = commentService.addComment(commentRequestDto,userId,postId);
            return new ResponseEntity(commentResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") int id){
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>("Comment deleted Successfully!!!",HttpStatus.CREATED);
        } catch (CommentNotFound e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
}
