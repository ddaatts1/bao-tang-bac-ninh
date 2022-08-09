package com.baotangbacninh.baotang.controller;

import com.baotangbacninh.baotang.Enum.Category;
import com.baotangbacninh.baotang.model.Posts;
import com.baotangbacninh.baotang.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicController {

    @Autowired
    PostsService postsService;

    @GetMapping("")
    public String index(Model model){
        return show(Category.Tin_Noi_Bat.toString(),1,model);

    }

    @GetMapping("{category}")
    public String show(@PathVariable String category,
                       @RequestParam int page,
                       Model model){
        Page<Posts> list = postsService.getTin(Category.valueOf(category),page-1);
        model.addAttribute("previousOrFirstPageable",list.previousOrFirstPageable().getPageNumber() ==0?1:list.previousOrFirstPageable().getPageNumber());
        model.addAttribute("nextOrLastPageable",list.nextOrLastPageable().getPageNumber()+1);
        model.addAttribute("totalPage",list.getTotalPages());
        model.addAttribute("currentPage",page==0?1:page);
        model.addAttribute("list", list);
        model.addAttribute("category", category);
        model.addAttribute("list",list);
        return "/public/public-home-page";

    }
    @GetMapping("/detail/{PostsId}")
    public String getdetail(@PathVariable int PostsId,Model model){
        model.addAttribute("posts",postsService.findById(PostsId));
        return "/public/public-posts-detail";
    }
    @GetMapping("/introduction")
    public String intro(Model model,@RequestParam Category category){
        if(postsService.findPostsByCategory(category).get().stream().count() >0)
        model.addAttribute("posts",postsService.findPostsByCategory(category).get().get(0));
        else model.addAttribute("posts",new Posts());
return "/public/introduction";    }

}
