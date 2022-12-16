package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.MemberService;

@RestController
@RequestMapping(value = "/api/member")
public class MemberRestController {
    
    @Autowired
    MemberService mService;

    // 아이디 중복확인
    // http://127.0.0.1:8085/QOOT3/api/member/idcheck.json?userid=
    @GetMapping(value="/idcheck.json")
    public Map<String, Object> idcheck(
        @RequestParam(name = "userid") String userid
    ){

        Map<String, Object> map = new HashMap<>();
        int ret = mService.idcheck(userid);
        System.out.println("-------------------------------------------controller-------------------------------");
        System.out.println(ret);
        System.out.println("-------------------------------------------controller-------------------------------");
        if(ret == 0){
            map.put("status", 200);
        }
        else{
            map.put("status", 0);
        }
        return map;
    }
}
