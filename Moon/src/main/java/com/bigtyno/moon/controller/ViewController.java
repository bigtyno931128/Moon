package com.bigtyno.moon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ViewController {

    // 할일 목록
    @GetMapping("/posts")
    public String posts(ModelMap map) {
        map.addAttribute("posts", List.of());
        return "posts/index";
    }

    // 할일 ( 상세 페이지 )
    @GetMapping("/posts/{postId}")
    public String post(@PathVariable Long postId, ModelMap map ) {
        map.addAttribute("post","post");
        map.addAttribute("postComments",List.of());

        return "posts/detail";
    }
}
