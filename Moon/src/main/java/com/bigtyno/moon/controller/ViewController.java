package com.bigtyno.moon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ViewController {

    @GetMapping("/")
    public String root() {
        return "forward:/posts/index";
    }

}
