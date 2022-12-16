package com.example.controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/cafe/admin")
public class AdminHomeContoller { 

    @GetMapping(value = "/home.do")
    public String homeGet(
    ){

        return "adminhome";
    }

}
