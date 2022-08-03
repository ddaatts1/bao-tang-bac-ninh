package com.baotangbacninh.baotang.repository;

import com.baotangbacninh.baotang.Enum.Category;
import com.baotangbacninh.baotang.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Integer> {

    List<Posts> findByPostsCategory(Category category);
    Page<Posts> findByPostsCategory(Category category,Pageable pageable);
}
