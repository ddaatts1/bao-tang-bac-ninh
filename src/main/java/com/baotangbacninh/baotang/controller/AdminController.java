package com.baotangbacninh.baotang.controller;

import com.baotangbacninh.baotang.Enum.Category;
import com.baotangbacninh.baotang.model.Posts;
import com.baotangbacninh.baotang.service.ImageService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    PostsService postsService;

    @Value("${file.path}")
    String path;
    @Autowired
    ImageService imageService;

    @GetMapping("")
    public String admin() {
        return "/admin/admin-home-page";
    }

    @GetMapping("/show")
    public String show(Model model,
            @RequestParam Category category,
            @RequestParam(required = false, defaultValue = "1") int page) {
        Page<Posts> list = postsService.getTin(category, page == 0 ? 0 : page - 1);
        model.addAttribute("previousOrFirstPageable", list.previousOrFirstPageable().getPageNumber() == 0 ? 1
                : list.previousOrFirstPageable().getPageNumber());
        model.addAttribute("nextOrLastPageable", list.nextOrLastPageable().getPageNumber() + 1);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", page == 0 ? 1 : page);
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
            @RequestParam(required = false) MultipartFile[] img) {
        if (bindingResult.hasErrors()) {
            return "/admin/add-posts";
        }
        // add posts
        posts.setDate(LocalDate.now());
        postsService.addPosts(posts);

        System.out.println(Arrays.stream(img).count());
        //add images
        if (Arrays.stream(img).count() > 1) {
            int postsId = postsService.getPostsByName(posts.getPostsName()).getId();
            Arrays.stream(img).forEach(i -> {
                imageService.addImage(postsId, i.getOriginalFilename());

                try {
                    FileCopyUtils.copy(i.getBytes(), new File(path + i.getOriginalFilename()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


        return show(model, posts.getPostsCategory(), 0);
    }

    @GetMapping("/addIntroduction")
    public String getformintroduction(@RequestParam Category category,Model model){

        if(postsService.findPostsByCategory(category).get().stream().count() ==0){
            model.addAttribute("posts",new Posts(category));
        }
        else {
            model.addAttribute("posts",postsService.findPostsByCategory(category).get().get(0));
        }
        return "/admin/add-introduction";
    }

    @PostMapping("/addIntroduction")
    public String addIntroduction(@ModelAttribute Posts posts){
        if(postsService.findPostsByCategory(posts.getPostsCategory()).get().stream().count() !=0){
            int id = postsService.findPostsByCategory(posts.getPostsCategory()).get().get(0).getId();
            postsService.updatePosts(id,posts);
        }
        else {
            postsService.addPosts(posts);
        }
        return "/admin/add-introduction";
    }

    @GetMapping("/remove/{PostsId}")
    public String removePosts(@PathVariable int PostsId, Model model) {
        Category category = postsService.findById(PostsId).getPostsCategory();
        postsService.removeposts(PostsId);
        return show(model, category, 1);
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

        postsService.updatePosts(PostsId, posts);

        return show(model, posts.getPostsCategory(), 1);
    }

}
