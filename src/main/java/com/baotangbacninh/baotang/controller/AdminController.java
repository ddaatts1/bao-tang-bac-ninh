package com.baotangbacninh.baotang.controller;

import com.baotangbacninh.baotang.Enum.Category;
import com.baotangbacninh.baotang.model.Posts;
import com.baotangbacninh.baotang.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    PostsService postsService;

    @GetMapping("")
    public String admin(){
        return "/admin/admin-home-page";
    }

    @GetMapping("/show")
    public String show(Model model, @RequestParam Category category){
        System.out.println(category);
            model.addAttribute("list",postsService.getTinNoiBat(category));
            postsService.getTinNoiBat(category).stream().forEach(t -> System.out.println(t.isPublish()));
           model.addAttribute("category",category);
            return "/admin/show";
    }

    @GetMapping("/add")
    public String addPosts(@RequestParam Category category,Model model){
        model.addAttribute("posts",new Posts(category));
        return "/admin/add-posts";
    }

    @PostMapping("add")
    public String addSubmit(@ModelAttribute Posts posts, Model model){
    posts.setPublish(true);
        System.out.println(posts);
        postsService.addPosts(posts);
        return show(model,posts.getPostsCategory());
    }

    @GetMapping("/remove/{PostsId}")
    public String removePosts(@PathVariable int PostsId,Model model){
        Category category = postsService.findById(PostsId).getPostsCategory();
        postsService.removeposts(PostsId);
        return show(model,category);
    }

    @GetMapping("/edit/{PostsId}")
    public String editPostsForm(@PathVariable int PostsId, Model model){
        model.addAttribute("posts",postsService.findById(PostsId));

        return "/admin/edit-posts-form";
    }
    @PostMapping("/edit/{PostsId}")
    public String submitEdit(@ModelAttribute Posts posts,@PathVariable int PostsId, Model model){
        postsService.updatePosts(PostsId,posts);

        return show(model,posts.getPostsCategory());
    }

}
