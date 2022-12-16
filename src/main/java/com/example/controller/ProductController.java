package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.ProductDTO;
import com.example.dto.ProductImageDTO;
import com.example.entity.Product;
import com.example.repository.ProductImageRepository;
import com.example.service.ProductImageService;
import com.example.service.ProductService;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired ProductService pService;
    @Autowired ProductImageService iService;
    @Autowired ProductImageRepository pRepository;
    
    //http://127.0.0.1:8085/QOOT1/product/list.do?category=drink
    @GetMapping(value = "/list.do")
    public String listGET(Model model
    ,@RequestParam(name="category",defaultValue = "drink", required = false) String category,
    @RequestParam(name="page", defaultValue = "1") int page 
    ){
        
        if (category.equals("drink")) {
            // System.out.println(total+"total");
            // System.out.println("음료");
            Map<String, Object> map = new HashMap<>();
            map.put("start", page*12-11);
            map.put("end", page*12);
            map.put("category", "drink"); 
    
            long total = pService.countList(category);
            List<ProductDTO> drinklist = pService.selectCategoryList(map);
            
            // System.out.println(drinklist.toString());
            
            for(ProductDTO product : drinklist){
                ProductImageDTO obj =iService.selectImageNoOne2(product.getNo());
                product.setImage("/productimage/image?no="+obj.getNo()); 
            }
            model.addAttribute("drinklist", drinklist);
            model.addAttribute("pages", (total-1)/12+1);
        }

        if (category.equals("dessert")) {
        //    System.out.println("디저트");
           Map<String, Object> map = new HashMap<>();
           map.put("start", page*12-11);
           map.put("end", page*12);
           map.put("category", "dessert");
           long total = pService.countList(category);

           List<ProductDTO> desertlist = pService.selectCategoryList(map);
           
           for(ProductDTO product : desertlist){
                ProductImageDTO obj =iService.selectImageNoOne2(product.getNo());
                product.setImage("/productimage/image?no="+obj.getNo());
            }
        //    System.out.println(desertlist);
           model.addAttribute("desertlist", desertlist);
           model.addAttribute("pages", (total-1)/12+1);
        }

        if (category.equals("goods")) {
        //    System.out.println("상품");
           Map<String, Object> map = new HashMap<>();
           map.put("start", page*12-11);
           map.put("end", page*12);
           map.put("category", "goods");
   
           long total = pService.countList(category);
           List<ProductDTO> goodslist = pService.selectCategoryList(map);

           for(ProductDTO product : goodslist){
                ProductImageDTO obj =iService.selectImageNoOne2(product.getNo());
                product.setImage("/productimage/image?no="+obj.getNo());
            }

        //    System.out.println(goodslist);
           model.addAttribute("goodslist", goodslist);
           model.addAttribute("pages", (total-1)/12+1);

        }

        return "product/product";
    }

    

    // http://127.0.0.1:8085/QOOT1/product/productone.do?no=100002
    @GetMapping(value = "/productone.do")
    public String insertGET(@RequestParam(name = "no")Long no,Model model){
        System.out.println(no);
        Product pro = pService.productOne(no);
        // System.out.println("==========pro==========="+pro);
        
        List<ProductImageDTO> image = iService.selectimageList(no);
        // System.out.println("==========image==========="+image);

        // ProductDTO nextandpre = pService.selectNextandPre(no);
        // ProductImage image2 = iService.selectImageNoOne(no);
        // nextandpre.setImage("/QOOT1/productimage/image?no="+image2.getNo());

        // System.out.println(image2);
        // System.out.println("===================next");
        // System.out.println(nextandpre);
        // System.out.println("===================next");

        // /////////////////////////// 이전글,다음글 시작
        Long next = pService.nextpageFAQ(no);
        Long prev = pService.prevpageFAQ(no);
        
        Product nextproduct = pService.productOne(next);
        List<ProductImageDTO> nextimage = iService.selectimageList(next);

        Product prevproduct = pService.productOne(prev);
        List<ProductImageDTO> previmage = iService.selectimageList(prev);
        
        // System.out.println(next);
        // System.out.println(prev);
        model.addAttribute("next", next);
        model.addAttribute("prev", prev);
        model.addAttribute("nextproduct", nextproduct);
        model.addAttribute("prevproduct", prevproduct);
        model.addAttribute("nextimage", nextimage);
        model.addAttribute("previmage", previmage);
        // ////////////////////////// 이전글,다음글 끝
        
        // ////////////////////////// 랜덤리스트 시작
        List<ProductDTO> random = pService.randomProduct();
        for(ProductDTO prod : random){
            ProductImageDTO obj =iService.selectImageNoOne2(prod.getNo());
            prod.setImage("/productimage/image?no="+obj.getNo());
        }

        // System.out.println("================");
        // System.out.println(random);
        // System.out.println("================");
        model.addAttribute("random", random);
        // model.addAttribute("randomimage", randomimage);
        // ////////////////////////// 랜덤리스트 끝

        model.addAttribute("pro", pro);
        model.addAttribute("image", image);
        // model.addAttribute("nextandpre", nextandpre);

        return "product/productselectone";
    }

    
}
