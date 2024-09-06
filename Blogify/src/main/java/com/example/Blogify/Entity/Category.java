package com.example.Blogify.Entity;

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
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "title",length = 100,nullable = false)
    String title;

    @Column(name = "description")
    String description;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();
}
