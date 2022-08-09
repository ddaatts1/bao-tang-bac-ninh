package com.baotangbacninh.baotang.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "image_name")
    String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    Posts posts;

    public Image(String imageName, int postsId) {
        this.imageName = imageName;
        this.posts = new Posts();
        this.posts.setId(postsId);
    }
}
