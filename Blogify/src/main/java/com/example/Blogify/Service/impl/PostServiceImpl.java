package com.example.Blogify.Service.impl;

import com.example.Blogify.Dto.RequestDto.PostRequestDto;
import com.example.Blogify.Dto.ResponseDto.PostResponse;
import com.example.Blogify.Dto.ResponseDto.PostResponseDto;
import com.example.Blogify.Entity.Category;
import com.example.Blogify.Entity.Post;
import com.example.Blogify.Entity.User;
import com.example.Blogify.Exceptions.CategoryNotFound;
import com.example.Blogify.Exceptions.PostNotFound;
import com.example.Blogify.Exceptions.UserNotFoundException;
import com.example.Blogify.Repository.CategoryRepository;
import com.example.Blogify.Repository.PostRepository;
import com.example.Blogify.Repository.UserRepository;
import com.example.Blogify.Service.Int.PostService;
import com.example.Blogify.Transformer.PostTransformer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public PostResponseDto addPost(PostRequestDto postRequestDto, int userId, int categoryId) throws UserNotFoundException, CategoryNotFound {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User doesn't Exist!!");
        }
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(!optionalCategory.isPresent()){
            throw new CategoryNotFound("Category Not Found!!!");
        }
        User user = optionalUser.get();
        Category category = optionalCategory.get();

        Post post = PostTransformer.preparePost(postRequestDto);
        user.getPosts().add(post);
        post.setUser(user);
        category.getPosts().add(post);
        post.setCategory(category);

        Post savedPost = postRepository.save(post);

        return PostTransformer.prepareResponseDto(savedPost);
    }

    @Override
    public PostResponseDto updatePost(PostRequestDto postRequestDto, int postId) throws PostNotFound {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(!optionalPost.isPresent()){
            throw new PostNotFound("Post doesn't Exist!!");
        }
        Post post = optionalPost.get();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setImageName(postRequestDto.getImageName());
        Post savedPost = postRepository.save(post);
        return PostTransformer.prepareResponseDto(savedPost);
    }

    @Override
    public void deletePost(int postId) throws PostNotFound {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(!optionalPost.isPresent()){
            throw new PostNotFound("Post doesn't Exist!!");
        }
        postRepository.delete(optionalPost.get());

    }

    @Override
    public PostResponseDto getPostById(int postId) throws PostNotFound {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(!optionalPost.isPresent()){
            throw new PostNotFound("Post doesn't Exist!!");
        }
        Post post = optionalPost.get();
        return PostTransformer.prepareResponseDto(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Post> pagePost = postRepository.findAll(pageable);

        List<Post> postList = pagePost.getContent();

        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : postList){
            postResponseDtos.add(PostTransformer.prepareResponseDto(post));
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postResponseDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPage(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public List<PostResponseDto> getPostByCategory(int categoryId) throws CategoryNotFound {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(!optionalCategory.isPresent()){
            throw new CategoryNotFound("Category Not Found!!!");
        }
        Category category = optionalCategory.get();
        List<Post> postList = postRepository.findByCategory(category);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : postList){
            postResponseDtos.add(PostTransformer.prepareResponseDto(post));
        }
        return postResponseDtos;
    }

    @Override
    public List<PostResponseDto> getPostByUser(int userid) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userid);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User doesn't Exist!!");
        }
        User user = optionalUser.get();
        List<Post> postList = postRepository.findByUser(user);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : postList){
            postResponseDtos.add(PostTransformer.prepareResponseDto(post));
        }
        return postResponseDtos;
    }

    @Override
    public List<PostResponseDto> searchPost(String title) {
        List<Post> postList = postRepository.findByTitleContaining(title);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : postList){
            postResponseDtos.add(PostTransformer.prepareResponseDto(post));
        }
       // return postList.stream().map((post)->this.modelMapper.map(post,PostResponseDto.class)).collect(Collectors.toList());
        return postResponseDtos;
    }


}
