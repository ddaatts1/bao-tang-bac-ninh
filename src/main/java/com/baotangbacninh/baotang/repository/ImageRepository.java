package com.baotangbacninh.baotang.repository;

import com.baotangbacninh.baotang.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {

    @Query(value = "SELECT * FROM image i WHERE i.posts_id =?1",nativeQuery = true)
    List<Image> findByPostsId(int postsId);
}
