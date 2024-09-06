package com.example.Blogify.Repository;

import com.example.Blogify.Entity.Category;
import com.example.Blogify.Entity.Post;
import com.example.Blogify.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByTitleContaining(String title);

    List<Post> findByCategory(Category category);

    List<Post> findByUser(User user);
}
