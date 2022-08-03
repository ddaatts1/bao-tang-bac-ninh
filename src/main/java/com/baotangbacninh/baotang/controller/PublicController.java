package com.baotangbacninh.baotang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PublicController {


    @GetMapping("")
    public String index(){
        return "/public/public-home-page";
    }
}
