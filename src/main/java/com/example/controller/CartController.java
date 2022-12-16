package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CartDTO;
import com.example.dto.ProductImageDTO;
import com.example.service.AddressService;
import com.example.service.CartService;
import com.example.service.MemberService;
import com.example.service.ProductImageService;
import com.example.service.ProductService;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired HttpSession http;
    @Autowired CartService cService;
    @Autowired MemberService mService;
    @Autowired ProductService pService;
    @Autowired AddressService aService;
    @Autowired ProductImageService iService;

    @GetMapping(value = "/home.do")
    public String homeGET(HttpServletRequest request,Model model){
        String sessionid = request.getRemoteAddr();
        HttpSession session = request.getSession();
        // session.getAttribute("LOGIN");
        // session.getAttribute("TOKEN");
        
        // System.out.println("sessionid"+sessionid);
        
        // 디저트, 음료 리스트
        List<CartDTO> ddlist = cService.selectDesertandDrink(sessionid);
        for(CartDTO cart : ddlist){
            ProductImageDTO obj =iService.selectImageNoOne2(cart.getProductno());
            cart.setImage("/productimage/image?no="+obj.getNo());
        }
        // System.out.println("desertlist");
        // System.out.println(ddlist.toString());
        // System.out.println("desertlist");

        //장바구니 비어있는지 확인용
        boolean empty = ddlist.isEmpty();
        model.addAttribute("empty", empty);


        //장바구니 총갯수, 총가격 계산
        long sum = 0;
        for(int i=0; i<ddlist.size(); i++){
            sum += ddlist.get(i).getSUM();
            // System.out.println("sum"+sum);
            model.addAttribute("sum", sum);
        }
        long cnt = 0;
        for(int i=0; i<ddlist.size(); i++){
            cnt += ddlist.get(i).getCNT();
            // System.out.println("cnt"+cnt);
            model.addAttribute("cnt", cnt);
        }
        model.addAttribute("ddlist", ddlist);
        
        return "product/cart";
    }

    @PostMapping(value = "/insert.do")
    public String insertGET(@ModelAttribute CartDTO cart,HttpServletRequest request){
        System.out.println("cart"+cart);
        String sessionid = request.getRemoteAddr();
        
        cart.setSessionid(sessionid);
        cService.upsertCart(cart);

        HttpSession session = request.getSession();

        // 세션에서 url꺼내기(intercepter)
        String CurrentUrl = (String) session.getAttribute("CURRENT_URL");

        System.out.println(CurrentUrl);

        return "redirect:" + CurrentUrl;
    }

    //장바구니에서 수량수정 후 주문페이지로 이동한다
    @PostMapping(value = "/update.do")
    public String updatePOST(
    HttpServletRequest request, 
    @RequestParam(name="quantity")Long[] quantity,
    @RequestParam(name="productno")Long[] productno
    // @RequestParam(name="buy")Long[] buy
    ){
        // System.out.println(buy.length);
        List<CartDTO> list = new ArrayList<>();
        for(int i=0; i<quantity.length; i++){ 
            CartDTO caca = new CartDTO();
            caca.setProductno(productno[i]);
            caca.setQuantity(quantity[i]);
            list.add(caca);
        }
        cService.modifyCount(list);

        // System.out.println("=======================");
        // System.out.println(list.toString());
        // System.out.println("=======================");

        return "redirect:/order1/home.do";
    }

    @PostMapping(value = "/delete.do")
    public String deletePOST(@RequestParam(name = "delproductno") Long productno,@RequestParam(name = "delsessionid") String sessionid){
        // System.out.println("------------------delete-------------------------------");
        // System.out.println("productno"+productno);
        Map<String, Object> map = new HashMap<>();
        map.put("sessionid", sessionid);
        map.put("productno", productno);

        cService.deleteone(map);
        return "redirect:/cart/home.do";
    }

    @PostMapping(value = "/deletebatch.do")
    public String deletebatchPOST(@RequestParam(name="buy")List<Long> buy){
        // System.out.println(buy.toString());
        cService.deleteCartBatch(buy);
        return "redirect:/cart/home.do";
    }

    @PostMapping(value = "/deleteall.do")
    public String deleteallPOST(){
        cService.deleteall();
        return "redirect:/cart/home.do";
    }
}
