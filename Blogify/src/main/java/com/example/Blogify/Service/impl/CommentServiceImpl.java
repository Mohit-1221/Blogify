package com.example.Blogify.Service.impl;

import com.example.Blogify.Dto.RequestDto.CommentRequestDto;
import com.example.Blogify.Dto.ResponseDto.CommentResponseDto;
import com.example.Blogify.Dto.ResponseDto.PostResponseDto;
import com.example.Blogify.Entity.Comment;
import com.example.Blogify.Entity.Post;
import com.example.Blogify.Entity.User;
import com.example.Blogify.Exceptions.CommentNotFound;
import com.example.Blogify.Exceptions.PostNotFound;
import com.example.Blogify.Exceptions.UserNotFoundException;
import com.example.Blogify.Repository.CommentRepository;
import com.example.Blogify.Repository.PostRepository;
import com.example.Blogify.Repository.UserRepository;
import com.example.Blogify.Service.Int.CommentService;
import com.example.Blogify.Transformer.CommentTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Override
    public CommentResponseDto addComment(CommentRequestDto commentRequestDto, int userId, int postId) throws UserNotFoundException, PostNotFound {
        Optional<User> optionalUser= userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User Doesn't Exist!!!");
        }
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(!optionalPost.isPresent()){
            throw new PostNotFound("Post Doesn't Exist!!!");
        }
        User user = optionalUser.get();
        Post post = optionalPost.get();

        Comment comment = CommentTransformer.prepareComment(commentRequestDto,user,post);

        Comment savedComment = commentRepository.save(comment);

        return CommentTransformer.prepareResponseDto(savedComment);

    }

    @Override
    public void deleteComment(int commentId) throws CommentNotFound {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(!optionalComment.isPresent()){
            throw new CommentNotFound("Comment doesn't Exist!!");
        }
        commentRepository.delete(optionalComment.get());
    }
}
