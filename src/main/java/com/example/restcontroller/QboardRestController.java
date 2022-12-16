package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.qboard.QboardImageService;

@RestController
@RequestMapping(value = "/rest/qboard")
public class QboardRestController {
    @Autowired QboardImageService qService;

    // 사진삭제
    //http://127.0.0.1:8085/QOOT3/rest/review/imagedelete.json
    @DeleteMapping(value="/imagedelete.json")
    public Map<String, Object> imagedeleteDELETE(
        @RequestParam(name="no") Long no
    ){

        Map<String, Object> map = new HashMap<>(); 

        System.out.println(no + "no=============================================");

        // int ret = reviewImageService.deleteimageone(no);
        // if(ret == 1){
        //     map.put("status", 200);
            return map;
        // }
        // else{
        //     map.put("status", 0);
        //     return map;
        // }
    }
}
