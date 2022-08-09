package com.baotangbacninh.baotang.service;

import com.baotangbacninh.baotang.model.Image;
import com.baotangbacninh.baotang.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;


    @Override
    public void removeByPostsId(int postsId){
          List<Image> list =  imageRepository.findByPostsId(postsId);
          list.stream().forEach(i -> imageRepository.delete(i));
    }

    @Override
    public void addImage(int postsId, String img){
        Image newImage = new Image(img,postsId);
        imageRepository.save(newImage);
    }
}
