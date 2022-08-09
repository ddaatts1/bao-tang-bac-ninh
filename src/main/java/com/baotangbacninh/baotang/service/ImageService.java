package com.baotangbacninh.baotang.service;

public interface  ImageService {
    void removeByPostsId(int postsId);

    void addImage(int postsId, String img);
}
