package com.baotangbacninh.baotang.service;

import com.baotangbacninh.baotang.Enum.Category;
import com.baotangbacninh.baotang.model.Posts;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostsService {


    Page<Posts> getTin(Category category, int page);

    void addPosts(Posts posts);

    void removeposts(int id);

    Posts findById(int id);

    void updatePosts(int id, Posts posts);
}
