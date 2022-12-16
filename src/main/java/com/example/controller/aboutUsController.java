package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/about")
public class aboutUsController {
    

    @GetMapping(value = "/home.do")
    public String aboutGET(){
        return "about";
    }
    
}
