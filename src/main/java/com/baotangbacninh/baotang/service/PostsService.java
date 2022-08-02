package com.baotangbacninh.baotang.service;

import com.baotangbacninh.baotang.Enum.Category;
import com.baotangbacninh.baotang.model.Posts;

import java.util.List;

public interface PostsService {
    List<Posts> getTinNoiBat(Category category);

    void addPosts(Posts posts);

    void removeposts(int id);

    Posts findById(int id);

    void updatePosts(int id, Posts posts);
}
