package com.baotangbacninh.baotang.service;

import com.baotangbacninh.baotang.Enum.Category;
import com.baotangbacninh.baotang.model.Posts;
import com.baotangbacninh.baotang.repository.PostsRepository;
import com.baotangbacninh.baotang.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    PostsRepository postsRepository;


    @Override
    public Page<Posts> getTin(Category category, int page){
        return
                postsRepository.findByPostsCategory(category, PageRequest.of(page,5));
    }

    @Override
    public void addPosts(Posts posts){
        postsRepository.save(posts);
    }

    @Override
    public void removeposts(int id){
        Posts posts = postsRepository.findById(id).get();
        postsRepository.delete(posts);
    }

    @Override
    public Posts findById(int id){
        return postsRepository.findById(id).get();
    }

    @Override
    public void updatePosts(int id, Posts posts){
        Posts updatePosts = postsRepository.findById(id).get();
        updatePosts.setPublish(posts.isPublish());
        if(posts.getPostsImage()!= null)
        updatePosts.setPostsImage(posts.getPostsImage());
        updatePosts.setPostsContent(posts.getPostsContent());
        updatePosts.setPostsTitle(posts.getPostsTitle());
        updatePosts.setPostsSource(posts.getPostsSource());
        updatePosts.setPostsName(posts.getPostsName());

        postsRepository.save(updatePosts);
    }
}
