package com.example.controller.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.AddressAndPaylist;
import com.example.dto.AddressDTO;
import com.example.dto.AddressinfoDTO;
import com.example.entity.PayResult;
import com.example.service.AddressService;
import com.example.service.MemberService;
import com.example.service.Order1Service;
import com.example.service.ProductImageService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/admin/order")
public class AdminOrderController {
    
    @Autowired
    Order1Service oService;

    @Autowired
    ProductImageService iService;

    @Autowired
    MemberService memberService;

    @Autowired
    AddressService aService;

    // 주문 진행목록
    // http://127.0.0.1:8085/QOOT1/admin/order/orderlist.do
    @GetMapping(value = "/orderlist.do")
    public String orderinfoGET(
        Model model,
        @RequestParam(name = "page" , defaultValue = "1") int page,
        @RequestParam(name = "userid" , defaultValue = "") String userid
    ){

        Map<String, Object> map = new HashMap<>();
        
        map.put("type", 2);
        map.put("userid", userid);
        map.put("start", (page*10)-9);
        map.put("end", page*10);
        
        int count = oService.countforcustomer(map);
        if(count != -1){
            int pages = (count-1)/10 +1;
            model.addAttribute("pages", pages);
        }
        else{
            model.addAttribute("pages", 1);
        }
        
        List<PayResult> list = oService.selectType2foradmin(map);
        List<AddressAndPaylist> aplist = new ArrayList<>();

        String tmp = "";
        
		for(PayResult obj : list){
            
            if(!tmp.equals(obj.getPaycode())){
                
                map.put("paycode", obj.getPaycode());
                List<AddressinfoDTO> addresslist = new ArrayList<>();
                List<PayResult> llist = oService.selectType0foradmin(map);
                for(PayResult oobj : llist){
                    Long imgno =iService.selectImageNoOne(oobj.getProductno());
                    oobj.setProductimageurl("/productimage/image?no="+imgno);
                }
                
                AddressinfoDTO newadd = new AddressinfoDTO();
                // 연락처
                String phone = memberService.memberSelectone(obj.getUserid()).getPhone();
                // 이메일
                String email = memberService.memberSelectone(obj.getUserid()).getEmail();
                // 주소 정보 조회
                AddressDTO addressinfo = aService.selectaddressByno(obj.getAddressno());
                if(addressinfo != null){
                    String address = "";
                    if(addressinfo.getAddress3() == null){
                        address = addressinfo.getAddress() + " " + addressinfo.getAddress2();
                        newadd.setAddress(address);
                    }
                    else{
                        address = addressinfo.getAddress() + " " + addressinfo.getAddress2() + " " + addressinfo.getAddress3();
                        newadd.setAddress(address);
                    }
                }
                newadd.setEmail(email);
                newadd.setPhone(phone);
                
                addresslist.add(newadd);
                
                AddressAndPaylist ap = new AddressAndPaylist();
                ap.setAList(addresslist);
                ap.setPList(llist);

                aplist.add(ap);

                tmp = obj.getPaycode();
            }
            
        } 

		model.addAttribute("list", aplist);
        
        return "product/adminorder/adminorderlist";
    }
    
    // 주문 완료 목록
    // http://127.0.0.1:8085/QOOT1/admin/order/completeorderlist.do
    @GetMapping(value = "/completeorderlist.do")
    public String completeorderinfoGET(
        Model model,
        @RequestParam(name = "page" , defaultValue = "1") int page,
        @RequestParam(name = "userid" , defaultValue = "") String userid
    ){

        Map<String, Object> map = new HashMap<>();
        
        map.put("type", 0);
        map.put("userid", userid);
        map.put("start", (page*10)-9);
        map.put("end", page*10);
        
        int count = oService.countforcustomer(map);
        if(count != -1){
            int pages = (count-1)/10 +1;
            model.addAttribute("pages", pages);
        }
        else{
            model.addAttribute("pages", 1);
        }
        
        List<PayResult> list = oService.selectType2foradmin(map);
        List<AddressAndPaylist> aplist = new ArrayList<>();

        String tmp = "";
        
		for(PayResult obj : list){
            
            if(!tmp.equals(obj.getPaycode())){
                
                map.put("paycode", obj.getPaycode());
                List<AddressinfoDTO> addresslist = new ArrayList<>();
                List<PayResult> llist = oService.selectType0foradmin(map);
                for(PayResult oobj : llist){
                    Long imgno =iService.selectImageNoOne(oobj.getProductno());
                    oobj.setProductimageurl("/productimage/image?no="+imgno);
                }
                
                AddressinfoDTO newadd = new AddressinfoDTO();
                // 연락처
                String phone = memberService.memberSelectone(obj.getUserid()).getPhone();
                // 이메일
                String email = memberService.memberSelectone(obj.getUserid()).getEmail();
                // 주소 정보 조회
                AddressDTO addressinfo = aService.selectaddressByno(obj.getAddressno());
                String address = "";
                if(addressinfo != null){
                    if(addressinfo.getAddress3() == null){
                        address = addressinfo.getAddress() + " " + addressinfo.getAddress2();
                        newadd.setAddress(address);
                    }
                    else{
                        address = addressinfo.getAddress() + " " + addressinfo.getAddress2() + " " + addressinfo.getAddress3();
                        newadd.setAddress(address);
                    }
                }
                newadd.setEmail(email);
                newadd.setPhone(phone);
                
                addresslist.add(newadd);
                
                AddressAndPaylist ap = new AddressAndPaylist();
                ap.setAList(addresslist);
                ap.setPList(llist);

                aplist.add(ap);

                tmp = obj.getPaycode();
            }
            
        } 

		model.addAttribute("list", aplist);
        
        return "product/adminorder/admincompleteorder";
    }
}
