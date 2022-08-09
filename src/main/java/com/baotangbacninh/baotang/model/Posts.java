package com.baotangbacninh.baotang.model;

import com.baotangbacninh.baotang.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    @NotBlank(message = "Tên bài không được để trống!")
    String postsName;
    @Column(name = "publish")
    boolean publish;
    @Column(name = "posts_source")
    String postsSource;
    @NotBlank
    @Column(name = "posts_title")
    String postsTitle;
    @Column(name = "posts_content")
            @NotBlank
    String postsContent;
    @Column(name = "posts_category")
    Category postsCategory;
    @Column(name = "date")
    LocalDate date;

    @OneToMany(mappedBy = "posts")
    List<Image> imageList;

    public String getPostsImage(){
        if(!imageList.isEmpty())
        return imageList.get(0).getImageName();
        return "lion.png";
    }

    public Posts(Category postsCategory) {
        this.postsCategory = postsCategory;
    }
}
