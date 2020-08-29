package com.cricket.ipl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    // examples: @RequestMapping({ "/home", "/home/predict", "/home/**"})
    @RequestMapping({ "/", "/login", "/registration", "/home", "/home/**"})
    public String index() {
        return "forward:/index.html";
    }
}
