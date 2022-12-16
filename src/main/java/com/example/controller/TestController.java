package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/test")
public class TestController {
    
    // 127.0.0.1:8085/QOOT3/test/list.do
    @GetMapping(value = "/list.do" )
    public String ProductGET(){
        return "product";
    }

    // 127.0.0.1:8085/QOOT3/test/nav.do
    @GetMapping(value = "/nav.do" )
    public String navbarGET(){
        return "test/test";
    }
}
