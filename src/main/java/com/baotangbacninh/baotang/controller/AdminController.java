package com.baotangbacninh.baotang.controller;

import com.baotangbacninh.baotang.Enum.Category;
import com.baotangbacninh.baotang.model.Posts;
import com.baotangbacninh.baotang.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    PostsService postsService;

    @Value("${file.path}")
    String path;

    @GetMapping("")
    public String admin() {
        return "/admin/admin-home-page";
    }

    @GetMapping("/show")
    public String show(Model model,
                            @RequestParam Category category,
                            @RequestParam(required = false,defaultValue = "1") int page) {
        Page<Posts> list = postsService.getTin(category,page == 0?0:page-1);
        model.addAttribute("previousOrFirstPageable",list.previousOrFirstPageable().getPageNumber() ==0?1:list.previousOrFirstPageable().getPageNumber());
        model.addAttribute("nextOrLastPageable",list.nextOrLastPageable().getPageNumber()+1);
        model.addAttribute("totalPage",list.getTotalPages());
        model.addAttribute("currentPage",page==0?1:page);
        model.addAttribute("list", list);
        model.addAttribute("category", category);
        return "/admin/show";
    }

    @GetMapping("/add")
    public String addPosts(@RequestParam Category category, Model model) {
        model.addAttribute("posts", new Posts(category));
        return "/admin/add-posts";
    }

    @PostMapping("add")
    public String addSubmit(@Valid @ModelAttribute Posts posts,
                            BindingResult bindingResult,
                            Model model,
                            @RequestParam(required = false) MultipartFile img
    ) {
        if (bindingResult.hasErrors()) {
            return "/admin/add-posts";
        }
        posts.setDate(LocalDate.now());
        if (!img.isEmpty()) {
            posts.setPostsImage(img.getOriginalFilename());
            try {
                FileCopyUtils.copy(img.getBytes()
                        , new File(path + img.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        postsService.addPosts(posts);
        return show(model, posts.getPostsCategory(),0);
    }

    @GetMapping("/remove/{PostsId}")
    public String removePosts(@PathVariable int PostsId, Model model) {
        Category category = postsService.findById(PostsId).getPostsCategory();
        postsService.removeposts(PostsId);
        return show(model, category,1);
    }

    @GetMapping("/edit/{PostsId}")
    public String editPostsForm(@PathVariable int PostsId, Model model) {
        model.addAttribute("posts", postsService.findById(PostsId));

        return "/admin/edit-posts-form";
    }

    @PostMapping("/edit/{PostsId}")
    public String submitEdit(@Valid @ModelAttribute Posts posts,
                             BindingResult bindingResult,
                             @PathVariable int PostsId,
                             Model model,
                             @PathVariable(required = false) MultipartFile img) {
        if (bindingResult.hasErrors()) {
            return "/admin/edit-posts-form";
        }

        if (!img.isEmpty()) {
            posts.setPostsImage(img.getOriginalFilename());
            try {
                FileCopyUtils.copy(img.getBytes()
                        , new File(path + img.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        postsService.updatePosts(PostsId, posts);

        return show(model, posts.getPostsCategory(),1);
    }



}
