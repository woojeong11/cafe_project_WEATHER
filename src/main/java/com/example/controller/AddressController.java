package com.example.controller;

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

import com.example.dto.AddressDTO;
import com.example.service.AddressService;
import com.example.service.MemberService;
import com.example.service.SecurityLogService;

@Controller
@RequestMapping(value = "/address")
public class AddressController {
    @Autowired AddressService aService;
    @Autowired SecurityLogService sService;
    @Autowired MemberService mService;

    @GetMapping(value = "/home.do")
    public String homeGET(HttpServletRequest request, Model model){
        String userid = (String)request.getAttribute("userid");
        System.out.println("userid"+userid);

        List<AddressDTO> list = aService.selectList(userid);
        model.addAttribute("list", list);
        model.addAttribute("userid", userid);

        return "address/home";
    }

    // 배송지 등록
    // http://127.0.0.1:8085/QOOT1/address/insert.do
    @PostMapping(value = "/insert.do")
    public String insertPOST(
        @ModelAttribute AddressDTO address,
        HttpServletRequest request
    ){
        
        Long no = aService.addressSeq();
        
        address.setNo(no);
        address.setUserid(request.getAttribute("userid").toString());
        
        aService.insertAddress(address);

        return "redirect:/member/orderinfo/myaddress.do";
    }

    // 대표배송지 변경
    // http://127.0.0.1:8085/QOOT1/address/repupdate.do
    @PostMapping(value = "/repupdate.do")
    public String repupdatePOST(
        HttpServletRequest request,
        @RequestParam(name = "no") Long no,
        @RequestParam(name = "page") int page
    ){
        
        Map<String, Object> map = new HashMap<>();
        map.put("userid", request.getAttribute("userid").toString());
        map.put("no", no);

        aService.updaterep(map);
        
        HttpSession session = request.getSession();

        String CurrentUrl = (String) session.getAttribute("CURRENT_URL");
        System.out.println(CurrentUrl);
        return "redirect:/member/orderinfo/myaddress.do?page=" + page;
    }

    // 배송지 삭제
    // http://127.0.0.1:8085/QOOT1/address/delete.do
    @PostMapping(value = "/delete.do")
    public String deletePOST(
        @RequestParam(name = "no") Long no
    ){
        System.out.println("---------------------삭제-------------------------");
        System.out.println(no);
        System.out.println("---------------------삭제-------------------------");
        aService.deleteaddress(no);

        return "redirect:/member/orderinfo/myaddress.do";
    }


    @GetMapping(value = "/addresslist.do")
    public String addresslistGET(
        HttpServletRequest request,
        Model model
    ){
        String userid = request.getAttribute("userid").toString();
        System.out.println(userid+"=============================================================");
        List<AddressDTO> list = aService.selectList(userid);
        model.addAttribute("list", list);
        System.out.println(list+"=============================================================" );
        return "address/address";
    }
}
