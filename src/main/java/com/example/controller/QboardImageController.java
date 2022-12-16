package com.example.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.QboardImageDTO;
import com.example.service.qboard.QboardImageService;

@Controller
@RequestMapping(value = "/qboardimage")
public class QboardImageController {
    @Autowired ResourceLoader resourceLoader;
    @Autowired QboardImageService iService;


    //이미지url
    //http://127.0.0.1:8085/QOOT3/qboardimage/image?no=1
	@GetMapping(value = "/image")
	public ResponseEntity<byte[]> imageGET(
	    @RequestParam(name="no") Long no) throws IOException{
	        // ProductImageDTO image = iService.selectImageOne(no);
            // System.out.println(no+"ddddddddddddddddddddddddddddddddd");
            if(no>0L){
                QboardImageDTO image = iService.selectImageOne(no);
                if(image.getImagesize() > 0L) {
                // 타입설정 png인지 jpg인지 gif인지
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(
                    MediaType.parseMediaType( image.getImagetype() ) );
                    // 실제이미지데이터, 타입이포함된 header, status 200    
                    ResponseEntity<byte[]> response 
                    = new ResponseEntity<>(
                        image.getImagedata(), headers, HttpStatus.OK);
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

    @PostMapping(value="/delete.do")
    public String deleteQboard(@RequestParam(name = "no")Long no,
        @RequestParam(name = "boardno")Long boardno) {
        System.out.println(no);
        iService.deleteImageOne(no);
        return "redirect:/qboard/update.do?no="+boardno;
    }
    
}
