package com.example.Blogify.Controller;

import com.example.Blogify.Config.AppConstants;
import com.example.Blogify.Dto.RequestDto.PostRequestDto;
import com.example.Blogify.Dto.ResponseDto.PostResponse;
import com.example.Blogify.Dto.ResponseDto.PostResponseDto;
import com.example.Blogify.Exceptions.CategoryNotFound;
import com.example.Blogify.Exceptions.PostNotFound;
import com.example.Blogify.Exceptions.UserNotFoundException;
import com.example.Blogify.Repository.PostRepository;
import com.example.Blogify.Service.Int.FileService;
import com.example.Blogify.Service.Int.PostService;
import com.example.Blogify.Transformer.PostTransformer;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String path;
    @PostMapping("/user/{userId}/category/{categoryId}/add")
    public ResponseEntity addPost(
            @RequestBody PostRequestDto postRequestDto,
            @PathVariable int userId,
            @PathVariable int categoryId)
    {
        try {
            PostResponseDto postResponseDto = postService.addPost(postRequestDto,userId,categoryId);
            return new ResponseEntity(postResponseDto,HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{postId}")
    public ResponseEntity updatePost(
            @RequestBody PostRequestDto postRequestDto,
            @PathVariable("postId") int id)
    {
        try {
            PostResponseDto postResponseDto = postService.updatePost(postRequestDto,id);
            return new ResponseEntity<>(postResponseDto,HttpStatus.CREATED);
        } catch (PostNotFound e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity deletePost(@PathVariable("postId") int id){
        try {
            postService.deletePost(id);
            return new ResponseEntity<>("Deleted Successfully!!",HttpStatus.CREATED);
        } catch (PostNotFound e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get/{postId}")
    public ResponseEntity getPostById(@PathVariable("postId") int id){

        try {
            PostResponseDto postResponseDto = postService.getPostById(id);
            return new ResponseEntity<>(postResponseDto,HttpStatus.CREATED);
        } catch (PostNotFound e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
           )
    {
        PostResponse postResponse = postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.CREATED);
    }

    @GetMapping("get/category/{categoryId}")
    public ResponseEntity getPostByCategory(@PathVariable("categoryId") int categoryId){
        try {
            List<PostResponseDto> postResponseDtos = postService.getPostByCategory(categoryId);
            return new ResponseEntity<>(postResponseDtos,HttpStatus.CREATED);
        } catch (CategoryNotFound e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity getPostByUser(@PathVariable("userId") int userId)  {
        try {
            List<PostResponseDto> postResponseDtos = postService.getPostByUser(userId);
            return new ResponseEntity<>(postResponseDtos,HttpStatus.CREATED);
        }
        catch (UserNotFoundException e) {
           return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/search/{keywords}")
    public ResponseEntity searchPost(@PathVariable("keywords") String keyWord){
        List<PostResponseDto> postResponseDtos = postService.searchPost(keyWord);
        return new ResponseEntity<>(postResponseDtos,HttpStatus.CREATED);
    }

    @PostMapping("/upload/image/post/{postId}")
    public ResponseEntity uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("postId") Integer id
            )
    {
        try {
            PostResponseDto postResponseDto = postService.getPostById(id);
            String fileName = fileService.uploadImage(path,image);
            postResponseDto.setImageName(fileName);

            PostRequestDto postRequestDto = PostTransformer.prepareRequestDto(postResponseDto,fileName);
            PostResponseDto postResponseDto1 = postService.updatePost(postRequestDto,id);

            return new ResponseEntity<>(postResponseDto1,HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response)
    {
        try {
            InputStream resouce = fileService.getResource(path,imageName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resouce,response.getOutputStream());
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
