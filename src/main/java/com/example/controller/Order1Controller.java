package com.example.controller;

import java.util.ArrayList;
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
import com.example.dto.CartDTO;
import com.example.dto.MemberDTO;
import com.example.dto.Order1DTO;
import com.example.dto.ProductImageDTO;
import com.example.dto.kakaopay.kakopayready;
import com.example.entity.Member;
import com.example.mapper.Order1Mapper;
import com.example.repository.CartRepository;
import com.example.repository.Order1Repository;
import com.example.service.AddressService;
import com.example.service.CartService;
import com.example.service.MemberService;
import com.example.service.Order1Service;
import com.example.service.ProductImageService;

import lombok.val;

@Controller
@RequestMapping(value = "/order1")
public class Order1Controller {
    @Autowired Order1Mapper oMapper;
    @Autowired MemberService mService;
    @Autowired CartService cService;
    @Autowired ProductImageService iService;
    @Autowired AddressService aService;
    @Autowired CartRepository cRepository;
    @Autowired Order1Repository oRepository;

    @Autowired Order1Service oService;
    @Autowired PayController payController;

    @GetMapping(value = "/home.do")
    public String orderGET(
        HttpServletRequest request,Model model
       ){
        // 주문서에서 주문자 정보 불러오기
        String userid = (String)request.getAttribute("userid");
        Member member = mService.memberSelectone(userid);
        
        //주소목록 modal용 배송지 list
        List<AddressDTO> addresslist = aService.selectList(userid);

        //주문서에서 주문자 주소 불러오기(대표설정)
        AddressDTO adresslist = aService.selectRownum(userid);

        String sessionid = request.getRemoteAddr();
        // HttpSession session = request.getSession();
        
        List<CartDTO> ddlist = cService.selectDesertandDrink(sessionid);
        for(CartDTO cart : ddlist){ 
            ProductImageDTO obj =iService.selectImageNoOne2(cart.getProductno());
            cart.setImage("/productimage/image?no="+obj.getNo());
        }
        long sum = 0;
        for(int i=0; i<ddlist.size(); i++){
            sum += ddlist.get(i).getSUM();
            // System.out.println("sum"+sum);
            model.addAttribute("sum", sum);
        }
        
        long cnt = 0;
        for(int i=0; i<ddlist.size(); i++){
            cnt += ddlist.get(i).getCNT();
            model.addAttribute("cnt", cnt);
        }


        model.addAttribute("member", member);
        model.addAttribute("adresslist", adresslist);
        model.addAttribute("addresslist", addresslist);
        model.addAttribute("ddlist", ddlist);

        
        return "product/order1";
    }

    // 주문처리(+주소저장도 함께)
    @PostMapping(value = "/insert.do")
    public String insertPOST(
        @RequestParam(name="quantity") Long[] quantity,
        @RequestParam(name="price") Long[] price,
        @RequestParam(name="productno") Long[] productno,
        @RequestParam(name="name") String name,
        @RequestParam(name="tprice") Long tprice,
        @RequestParam(name="type") Long type,
        @RequestParam(name="msg") String msg,
        @RequestParam(name="payment") String payment,
        @ModelAttribute AddressDTO address,
        Model model,
        HttpServletRequest request
        ) throws Exception{
            String userid = (String)request.getAttribute("userid");
            Map<String, Object> map = new HashMap<>();
            map.put("userid", userid);
            map.put("post", address.getPost());
            map.put("address", address.getAddress());
            map.put("address2", address.getAddress2());
            map.put("address3", address.getAddress3());
            AddressDTO addret = aService.selectaddressBymap(map);
            
            Long no = 0L;
            if(addret == null){
                no = aService.addressSeq();
                
                address.setNo(no);
                address.setUserid(userid);
                
                aService.insertAddress(address);
            }
            else{
                no = addret.getNo();
            }

            List<Order1DTO> list = new ArrayList<>();
            for(int i=0; i<quantity.length; i++){
                Order1DTO order = new Order1DTO();
                order.setQuantity(quantity[i]);
                order.setProductno(productno[i]);
                order.setAddressno(no);
                order.setName(name);
                order.setPrice(price[i]);
                order.setTprice(tprice);   
                order.setUserid(userid);   
                order.setMsg(msg);         
                order.setType(type); 
                list.add(order);
            }
            oMapper.deleteorder(userid);
            oMapper.insertOrderBatch(list);

            // 카카오 페이
            if(payment.equals("kakao")){
                String ready = payController.getMethodName(Integer.parseInt(tprice.toString()), userid, request).getNext_redirect_pc_url();
                return "redirect:" + ready;
            }
            // 네이버 페이
            else if(payment.equals("naver")){
                return "redirect:/pay/naver.do";
            }
            return "redirect:/order1/home.do";
    }

    // 주문취소
    @PostMapping(value = "/cancel.do")
    public String cancelDELETE(@RequestParam(name = "code") String code, @RequestParam(name = "page") int page){

        oService.cancelorder(code);

        return "redirect:/member/orderinfo/ing.do?page=" + page;
    }

    // 주문취소
    @PostMapping(value = "/cancel2.do")
    public String cancel2DELETE(@RequestParam(name = "code") String code){

        oService.cancelorder(code);

        return "redirect:/member/orderinfo/ing.do";
    }

    // 주문취소
    @PostMapping(value = "/cancel3.do")
    public String cancel3DELETE(@RequestParam(name = "code") String code, @RequestParam(name = "page") int page){

        oService.cancelorder(code);

        return "redirect:/admin/order/orderlist.do?page=" + page;
    }

}
