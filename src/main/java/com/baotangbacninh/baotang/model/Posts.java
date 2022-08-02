package com.baotangbacninh.baotang.model;

import com.baotangbacninh.baotang.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "posts_name")
    String postsName;
    @Column(name = "publish")
    boolean publish;
    @Column(name = "posts_source")
    String postsSource;
    @Column(name = "posts_image")
    String postsImage;
    @Column(name = "posts_title")
    String postsTitle;
    @Column(name = "posts_content")
    String postsContent;
    @Column(name = "posts_category")
    Category postsCategory;

    public Posts(Category postsCategory) {
        this.postsCategory = postsCategory;
    }
}
