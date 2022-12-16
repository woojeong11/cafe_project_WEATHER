package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.config.GlobalProperties;
import com.example.dto.CartDTO;
import com.example.entity.RecommendView;
import com.example.service.CartService;
import com.example.service.RecommendService;
import com.fasterxml.jackson.databind.util.JSONPObject;

@RestController
@RequestMapping(value = "/rest/recommend")
public class RecommendRestController {
    
    @Autowired
    RecommendService rService;

    @Autowired
    CartService cService;

    // 랜덤 추천
    // http://127.0.0.1:8085/QOOT1/rest/recommend/random.json?category=
    @GetMapping(value = "/random.json")
    public Map<String, Object> RecommendGET(
        @RequestParam(name = "category") String category 
    ){
        
        Map<String, Object> map = new HashMap<>();

        // 음료 추천
        RecommendView drink = rService.selectrandom(category, "drink");
        drink.setProductimageurl( GlobalProperties.servepath +"/productimage/productimage?no=" + drink.getProductno());

        // 디저트 추천
        RecommendView dessert = rService.selectrandom(category, "dessert");
        dessert.setProductimageurl( GlobalProperties.servepath +"/productimage/productimage?no=" + dessert.getProductno());
 
        map.put("drink", drink);
        map.put("dessert", dessert);

        return map;
    }

    // 랜덤 추천 카트담기
    // http://127.0.0.1:8085/QOOT1/rest/recommend/insert.json
    @PostMapping(value = "/insert.json")
    public Map<String, Object> insertPOST(
        @RequestBody String body,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();

        // RestTemplate restTemplate = new RestTemplate();
        JSONObject jsonObject = new JSONObject(body);

        System.out.println("cart"+ jsonObject.has("quantity") );
        String sessionid = request.getRemoteAddr();
        
        CartDTO cart = new CartDTO();

        cart.setProductno(Long.parseLong(jsonObject.getString("productno")));
        cart.setSessionid(sessionid);
        if( jsonObject.has("quantity")){
            cart.setQuantity(Long.valueOf(jsonObject.getString("quantity")));
        }
        else{
            cart.setQuantity(1L);
        }
        int ret = cService.upsertCart(cart);

        if(ret == 1){
            map.put("status", 200);
        }
        else{
            map.put("status", 0);
        }
        
        return map;
    }

    // 랜덤 추천 카트 모두 담기
    // http://127.0.0.1:8085/QOOT1/rest/recommend/insertall.json
    @PostMapping(value = "/insertall.json")
    public Map<String, Object> insertallPOST(
        @RequestBody String body,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();

        // RestTemplate restTemplate = new RestTemplate();
        JSONObject jsonObject = new JSONObject(body);

        System.out.println("cart"+ jsonObject.getString("productno") );
        System.out.println("cart"+ jsonObject.getString("productno1") );
        String sessionid = request.getRemoteAddr();
        
        CartDTO cart = new CartDTO();

        cart.setProductno(Long.parseLong(jsonObject.getString("productno")));
        cart.setSessionid(sessionid);
        cart.setQuantity(1L);
        int ret = cService.upsertCart(cart);
        
        cart.setProductno(Long.parseLong(jsonObject.getString("productno1")));
        int ret1 = cService.upsertCart(cart);

        if(ret == 1 && ret1 == 1){
            map.put("status", 200);
        }
        else{
            map.put("status", 0);
        }
        
        return map;
    }
}
