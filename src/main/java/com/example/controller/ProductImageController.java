package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductImageDTO;
import com.example.entity.Product;
import com.example.repository.ProductImageRepository;
import com.example.service.ProductImageService;
import com.example.service.ProductService;


@Controller
@RequestMapping(value = "/productimage")
public class ProductImageController {

    @Autowired ProductImageService iService;
    @Autowired ProductImageRepository iRepository;
    @Autowired ResourceLoader resourceLoader;

    @Autowired ProductService pService;

    
    @GetMapping(value = "/insert.do")
    public String insertGET(@RequestParam(name="no")Long no,Model model
        ){
            // System.out.println(no);
            List<ProductImageDTO> image = iService.selectimageList(no);
            model.addAttribute("obj", no);
            model.addAttribute("image", image);

        return "product/productimage_insert";
    }
    
    @GetMapping(value = "/insert2.do")
    public String insert2GET(@RequestParam(name="no")Long no,Model model
        ){
            Product pro = pService.productOne(no);
            // System.out.println(pro.toString());

            System.out.println(no);
            List<ProductImageDTO> image = iService.selectimageList(no);
            model.addAttribute("pro", pro);
            model.addAttribute("obj", no);
            model.addAttribute("image", image);

        return "product/admin_productimage_insert";
    }
    

    @PostMapping(value="/insert.do")
    public String insertPOST(
        @RequestParam(name="no")Long productno,
        @RequestParam(name="file") MultipartFile file)throws Exception{
            // System.out.println(file.getOriginalFilename());
            // System.out.println(productno);
            // System.out.println("============================");
    
            // ProductDTO pro = new ProductDTO();
            // pro.setNo(productno);

            ProductImageDTO image = new ProductImageDTO();
            image.setImagedata(file.getBytes());
            image.setImagename(file.getOriginalFilename());
            image.setImagesize(file.getSize());
            image.setImagetype(file.getContentType());
            image.setProductno(productno);
    
            iService.insertImage(image);
    
            // System.out.println(image.getProductno());
        return "redirect:/productimage/insert2.do?no="+productno;
    }
    

    
    //이미지url
    //http://127.0.0.1:8085/QOOT3/productimage/image?no=1
	@GetMapping(value = "/image")
	public ResponseEntity<byte[]> imageGET(
	    @RequestParam(name="no") Long no) throws IOException{
	        // ProductImageDTO image = iService.selectImageOne(no);
            // System.out.println(no+"ddddddddddddddddddddddddddddddddd");
            if(no>0L){
                ProductImageDTO item = iService.selectImageOne(no);
                if(item.getImagesize() > 0L) {
                // 타입설정 png인지 jpg인지 gif인지
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(
                    MediaType.parseMediaType( item.getImagetype() ) );
                    // 실제이미지데이터, 타입이포함된 header, status 200    
                    ResponseEntity<byte[]> response 
                    = new ResponseEntity<>(
                        item.getImagedata(), headers, HttpStatus.OK);
                        return response;        
            }
            else {
                InputStream is = resourceLoader.getResource("classpath:/static/img/image.png")
                .getInputStream();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                // 실제이미지데이터, 타입이포함된 header, status 200    
                ResponseEntity<byte[]> response 
                = new ResponseEntity<>(
                    is.readAllBytes(), headers, HttpStatus.OK);
                    return response;
            }
        }
        else {
            InputStream is = resourceLoader.getResource("classpath:/static/img/image.png")
                    .getInputStream();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            // 실제이미지데이터, 타입이포함된 header, status 200    
            ResponseEntity<byte[]> response 
                = new ResponseEntity<>(
                    is.readAllBytes(), headers, HttpStatus.OK);
            return response;
        }
    }

    // @GetMapping(value = "/imagelist.do")
	// public String itemImageGET(@RequestParam(name="no") Long productno, Model model){
	//     List<ProductImage> list = iService.selectimageList(productno);
	//     System.out.println(list.toString());
	//     //<img src 사용할 번호들
	//     model.addAttribute("list", list);
	//     model.addAttribute("productno", productno);
	//     return "seller/insert_item_image";
	// }

    @PostMapping(value = "/delete.do")
    public String deleteOnePOST(@RequestParam(name="no")Long no,@RequestParam(name="productno")Long productno){
        // System.out.println("deleteno"+no);
        iService.deleteImageOne(no);
        return "redirect:/productimage/insert2.do?no=" + productno;
    }

    @PostMapping(value = "/deletebatch.do")
    public String deleteBatchPOST(@RequestParam(name="productno")Long productno){
        // System.out.println("deleteproductno"+productno);
        iService.deleteImageBatch(productno);
        return "redirect:/productimage/insert2.do?no=" + productno;
    }

    //이미지url(productno 기반으로 검색) 
    //http://127.0.0.1:8085/QOOT3/productimage/productimage?no=
	@GetMapping(value = "/productimage")
	public ResponseEntity<byte[]> productimageGET(
	    @RequestParam(name="no") Long no) throws IOException{
            if(no>0L){
                ProductImageDTO item = iService.selectImageOneByproductno(no);
                if(item.getImagesize() > 0L) {
                // 타입설정 png인지 jpg인지 gif인지
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(
                    MediaType.parseMediaType( item.getImagetype() ) );
                    // 실제이미지데이터, 타입이포함된 header, status 200    
                    ResponseEntity<byte[]> response 
                    = new ResponseEntity<>(
                        item.getImagedata(), headers, HttpStatus.OK);
                        return response;        
            }
            else {
                InputStream is = resourceLoader.getResource("classpath:/static/img/image.png")
                .getInputStream();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                // 실제이미지데이터, 타입이포함된 header, status 200    
                ResponseEntity<byte[]> response 
                = new ResponseEntity<>(
                    is.readAllBytes(), headers, HttpStatus.OK);
                    return response;
            }
        }
        else {
            InputStream is = resourceLoader.getResource("classpath:/static/img/image.png")
                    .getInputStream();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            // 실제이미지데이터, 타입이포함된 header, status 200    
            ResponseEntity<byte[]> response 
                = new ResponseEntity<>(
                    is.readAllBytes(), headers, HttpStatus.OK);
            return response;
        }
    }
    
}