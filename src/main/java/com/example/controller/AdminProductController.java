package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductDTO;
import com.example.dto.ProductImageDTO;
import com.example.entity.Product;
import com.example.repository.ProductImageRepository;
import com.example.repository.ProductRepository;
import com.example.service.ProductImageService;
import com.example.service.ProductService;

@Controller
@RequestMapping(value = "/admin")
public class AdminProductController {
    @Autowired ProductService pService;
    @Autowired ProductRepository prorRepository;

    @Autowired ProductImageService iService;
    @Autowired ProductImageRepository pRepository;
    @Autowired ResourceLoader resourceLoader;

        // http://127.0.0.1:8085/QOOT1/admin/product/insert.do
        @GetMapping(value = "/product/insert.do")
        public String insertGET(){
            return "product/productinsert";
        }
    
        @PostMapping(value = "/product/insert.do")
        public String insertPOST(@ModelAttribute ProductDTO product,
        @RequestParam(name="file" , required = false) MultipartFile file
        )throws Exception{
            // System.out.println(product.toString());
            
            // System.out.println(file.getSize()+"파일사이즈");

            //시퀀스 먼저 읽기
            Long no = pService.productSeq();
            product.setNo(no);

            pService.insertProduct(product);
            
            if(file!=null){
                ProductImageDTO image = new ProductImageDTO();
                image.setImagedata(file.getBytes());
                image.setImagename(file.getOriginalFilename());
                image.setImagesize(file.getSize());
                image.setImagetype(file.getContentType());
                image.setProductno(no);
                iService.insertImage(image);
            }
            return "redirect:/admin/product/list.do?category="+product.getCategory();
        }
     
        @PostMapping(value = "/product/delete.do")
        public String deletePOST(@RequestParam(name = "no")Long no,
        @RequestParam(name = "category")String category,Model model){
            try{
                // System.out.println(no+"=============deleteno");
                iService.deleteImageBatch(no);
                pService.deleteProduct(no);
             
                return "redirect:/admin/product/list.do?category="+category;
            }
            catch(Exception e){
                return "redirect:/admin/product/list.do?category="+category;
            }
        }

        @GetMapping(value = "/product/list.do")
        public String prolistGET(Model model,@RequestParam(name="category",defaultValue = "", required = false) String category,
        @RequestParam(name="page", defaultValue = "1") int page) {
            
        if (category.equals("drink")) {
            // System.out.println("음료");

            Map<String, Object> map = new HashMap<>();
            map.put("start", page*12-11);
            map.put("end", page*12);
            map.put("category", "drink");
    
            long total = pService.countList(category);

            List<ProductDTO> drinklist = pService.selectCategoryList(map);

            for(ProductDTO product : drinklist){
                ProductImageDTO obj =iService.selectImageNoOne2(product.getNo());
                product.setImage("/productimage/image?no="+obj.getNo());
            }
            // System.out.println(drinklist);
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

            return "product/adminproductlist";
        }

        @GetMapping(value = "/product/update.do")
        public String updateGET(@RequestParam(name = "no")Long no,Model model){
            Product pro = prorRepository.findById(no).orElse(null);
            model.addAttribute("pro", pro);
            return "product/update";
        }

        @PostMapping(value = "/product/update.do")
        public String updatePOST(@ModelAttribute Product product,@RequestParam(name="category",defaultValue = "", required = false) String category){
            // System.out.println(product.toString());
            Product pro = prorRepository.findById(product.getNo()).orElse(null);
            pro.setName(product.getName());
            pro.setContent(product.getContent());
            pro.setPrice(product.getPrice());
            pro.setCategory(product.getCategory());
            prorRepository.save(pro);
                return "redirect:/admin/product/list.do?category="+category; 
            
        }
        
}
