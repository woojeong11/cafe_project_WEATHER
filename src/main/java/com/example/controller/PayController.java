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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CartDTO;
import com.example.dto.Order1DTO;
import com.example.dto.ProductImageDTO;
import com.example.dto.kakaopay.kakopayready;
import com.example.entity.PayResult;
import com.example.service.CartService;
import com.example.service.Order1Service;
import com.example.service.ProductImageService;
import com.example.service.kakaopay.KakaoPayService;

@Controller
@RequestMapping(value = "/pay")
public class PayController {
    
    @Autowired
    KakaoPayService kakaoPayService;
    
	@Autowired
	CartService cService;

	@Autowired
	ProductImageService iService;

	@Autowired
	Order1Service oService;

    @GetMapping(value="/kakao/paying.do")
    public kakopayready getMethodName(
        @RequestParam(name ="total_amount") int totalAmount, 
        @RequestParam(name ="userid") String userid, 
		HttpServletRequest request
    ){
		// 카카오 결제 준비하기	- 결제요청 service 실행.
		kakopayready readyResponse = kakaoPayService.payReady(totalAmount, userid);
		// 요청처리후 받아온 결재고유 번호(tid)를 세션에 저장
		HttpSession session = request.getSession();
		session.setAttribute("tid", readyResponse.getTid());
		
		// System.out.println(readyResponse.toString());
		return readyResponse; // 클라이언트에 보냄.(tid,next_redirect_pc_url이 담겨있음.) 
    }
    
    // 카카오 결제승인요청
	@GetMapping("/kakao/completed.do")
	public String payCompleted(@RequestParam("pg_token") String pgToken, Model model, HttpServletRequest request) {
		
        String userid = (String) request.getAttribute("userid");

		HttpSession session = request.getSession();
		String tid = (String) session.getAttribute("tid");
		
		// 카카오 결제 요청하기
		kakaoPayService.payApprove(tid, pgToken, userid);	

		Map<String, Object> map = new HashMap<>();
		map.put("paycode", tid);
		map.put("userid", userid);

		oService.updateList(map);
		String sessionid = request.getRemoteAddr();
		cService.deleteCart(sessionid);

		model.addAttribute("msg", "결제가 완료되었습니다.");
        model.addAttribute("url", 
        request.getContextPath() + "/pay/kakao/payresult.do" );

        return "alert/alert";
	}

	// 카카오 결제취소시에 넘어오는 url (주문 tbl에서 결제 중이던 건들 취소)
	@GetMapping("/kakao/paycancel.do")
	public String paycancel(
		HttpServletRequest request,
		Model model
	){
		
        String userid = (String) request.getAttribute("userid");	

		oService.deleteorder(userid);

		model.addAttribute("msg", "결제가 취소되었습니다.");
        model.addAttribute("url", 
        request.getContextPath() + "/cart/home.do" );

        return "alert/alert";
	}

	// 카카오 결제실패시
	@GetMapping("/kakao/payfail.do")
	public String payfail(HttpServletRequest request, Model model) {
		
        String userid = (String) request.getAttribute("userid");

		oService.deleteorder(userid);

		model.addAttribute("msg", "결제가 실패했습니다.");
        model.addAttribute("url", 
        request.getContextPath() + "/cart/home.do" );
		
		return "alert/alert";
	}

	// 네이버 리턴(결제코드 오는)
	// http://127.0.0.1:8085/QOOT1/pay/naver/paying.do
	@GetMapping(value="/naver/paying.do")
    public String naverreturn(
        @RequestParam(name ="paymentId", required = false) String paymentId,
        @RequestParam(name ="resultCode") String resultCode,
		HttpServletRequest request,
		Model model
    ){
		
		String userid = (String) request.getAttribute("userid");

		String sessionid = request.getRemoteAddr();
		Map<String, Object> map = new HashMap<>();
		
		if(resultCode.equals("Success")){
			
			map.put("paycode", paymentId);
			map.put("userid", userid);
			oService.updateList(map);
			cService.deleteCart(sessionid);
			
			model.addAttribute("msg", "결제가 완료되었습니다.");
			model.addAttribute("url", 
			request.getContextPath() + "/pay/kakao/payresult.do" );
	
			return "alert/alert";
		}
		else{
			oService.deleteorder(userid);
			
			return "redirect:/cart/home.do"	;
		}
    }

	// 네이버페이 페이지
	@GetMapping(value="/naver.do")
    public String payhome( 
		HttpServletRequest request,Model model
    ){

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
             // System.out.println("cnt"+cnt);
             model.addAttribute("cnt", cnt);
        }
		model.addAttribute("ddlist", ddlist);
        
		return "product/pay"; 
    }

	// kakao 주문결제완료 페이지
	// http://127.0.0.1:8085/QOOT1/pay/kakao/payresult.do
	@GetMapping(value="/kakao/payresult.do")
	public String kakaopayresult( HttpServletRequest request, Model model  ) {
		
	

		return "product/payresult";
	}
	
}
