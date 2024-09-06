package com.example.Blogify.Entity;

import com.example.Blogify.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "user_name")
    String name;

    @Column(name = "email_id",unique = true,nullable = false)
    String emailId;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "password",nullable = false)
    String password;

    @Column(name = "about")
    String about;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();
}
