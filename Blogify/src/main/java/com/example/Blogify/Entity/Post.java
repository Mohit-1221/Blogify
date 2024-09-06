package com.example.Blogify.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "title",nullable = false)
    String title;

    String content;

    String imageName;

    @CreationTimestamp
    Date dateOfPost;

    @ManyToOne
    @JoinColumn
    User user;

    @ManyToOne
    @JoinColumn
    Category category;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();
}
