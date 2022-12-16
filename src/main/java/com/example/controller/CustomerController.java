package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
    
    // 홈
    // 127.0.0.1:8085/QOOT3/login.do
    @GetMapping(value = "/login.do" )
    public String loginGET(){
        return "member/login";
    }
}
