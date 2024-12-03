package com.example.cricketApplication.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        // Return the React app's index.html
        return "forward:/index.html";
    }
}
