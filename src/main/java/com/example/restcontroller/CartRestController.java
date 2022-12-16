package com.example.restcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.CartService;

@RestController
@RequestMapping(value = "/rest/cart")
public class CartRestController {
    
    @Autowired
    CartService cService;

    // 장바구니 갯수
    // http://127.0.0.1:8085/QOOT3/rest/cart/count.json
    @GetMapping(value = "/count.json")
    public int countGET( HttpServletRequest request){

        String sessionid = request.getRemoteAddr();

        return cService.countcart(sessionid);
    };
    
}
