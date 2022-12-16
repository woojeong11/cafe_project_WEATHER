package com.example.controller.RecommendWeather;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.WeatherCategory;
import com.example.service.ProductImageService;
import com.example.service.ProductService;
import com.example.service.RecommendService;
import com.example.service.WeatherCategoryService;

@Controller
@RequestMapping(value = "/weather")
public class Weatherimage {
    
    @Autowired
    ProductService pService;

    @Autowired
    RecommendService rService;

    @Autowired
    ProductImageService iService;

    @Autowired
    WeatherCategoryService wcService;

    @Autowired
    ResourceLoader resourceLoader;
    
    // 배경 이미지 조회
    // 프로필 이미지 1개 조회( 유저아이디 기준)
    // http://127.0.0.1:8085/QOOT1/weather/imageselect?code=
    @GetMapping(value = "/imageselect")
    public ResponseEntity<byte[]> imageselectGET(
        @RequestParam(name="code") String code
    ) throws IOException{
        ResponseEntity<byte[]> response = null;

        WeatherCategory image = wcService.selectWeatherCategory(code);
        
        HttpHeaders headers = new HttpHeaders();
        if( image.getImagesize() > 0L ){
            headers.setContentType(MediaType.parseMediaType(image.getImagetype()));
            response = new ResponseEntity<byte[]>(image.getImagedata(), headers, HttpStatus.OK);
        }
        else{
            // resources/static/imgs 에 있는 이미지 파일을 불러온다. @Autowired로 불러온 리소스로더를 이용.
            InputStream stream = resourceLoader.getResource("classpath:/static/img/image.png").getInputStream();
            headers.setContentType(MediaType.IMAGE_PNG);
            response = new ResponseEntity<byte[]>(stream.readAllBytes(), headers, HttpStatus.OK);
        }

        return response;
    }
}
