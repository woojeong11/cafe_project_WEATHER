package com.example.service.kakaopay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.client.RestTemplate;

import com.example.config.GlobalProperties;
import com.example.dto.Order1DTO;
import com.example.dto.kakaopay.kakopayApprove;
import com.example.dto.kakaopay.kakopayready;
import com.example.entity.Product;
import com.example.mapper.Order1Mapper;
import com.example.mapper.ProductMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoPayService {

	@Autowired
    Order1Mapper order1Mapper;
    
    @Autowired
    ProductMapper productMapper;

    // 준비 단계
    public kakopayready payReady(int totalAmount, String userid) {
		
        Map<String, Object> map = new HashMap<>();

        map.put("userid", userid);

		List<Order1DTO> orderlist = order1Mapper.selectOrderList(map);
		String[] cartNames = new String[orderlist.size()];
		
		for(Order1DTO cart: orderlist) {
			for(int i=0; i< orderlist.size(); i++) {
                
				cartNames[i] = productMapper.productOne(cart.getProductno()).getName();
			}
		}	
		String itemName = cartNames[0] + " 그외" + (orderlist.size()-1);
		// log.info("주문들:"+itemName);
		String order_id = "cafeweather";
		
        // 카카오가 요구한 결제요청request값을 담아줍니다. 
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", "TC0ONETIME");
		parameters.add("partner_order_id", order_id);
		parameters.add("partner_user_id", userid);
		parameters.add("item_name", itemName);
		parameters.add("quantity", String.valueOf(orderlist.size()));
		parameters.add("total_amount", String.valueOf(totalAmount));
		parameters.add("tax_free_amount", "0");
		parameters.add("approval_url", "http://3.38.209.149:8080" + GlobalProperties.servepath + "/pay/kakao/completed.do"); // 결제승인시 넘어갈 url
		parameters.add("cancel_url", "http://3.38.209.149:8080" + GlobalProperties.servepath + "/pay/kakao/paycancel.do"); // 결제취소시 넘어갈 url
		parameters.add("fail_url", "http://3.38.209.149:8080" + GlobalProperties.servepath + "/pay/kakao/payfail.do"); // 결제 실패시 넘어갈 url
		
		// log.info("파트너주문아이디:"+ parameters.get("partner_order_id")) ;
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(parameters, this.getHeaders());
		// 외부url요청 통로 열기.
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/ready";
        // template으로 값을 보내고 받아온 ReadyResponse값 readyResponse에 저장.
		kakopayready readyResponse = template.postForObject(url, body, kakopayready.class);
		log.info("결재준비 응답객체: " + readyResponse);
        // 받아온 값 return
		return readyResponse;
	}

    // 결제 승인요청 메서드
	public kakopayApprove payApprove(String tid, String pgToken, String userid) {
		Map<String, Object> map = new HashMap<>();

        map.put("userid", userid);

		List<Order1DTO> orderlist = order1Mapper.selectOrderList(map);
		String[] cartNames = new String[orderlist.size()];
		
		for(Order1DTO cart: orderlist) {
			for(int i=0; i< orderlist.size(); i++) {
                
				cartNames[i] = productMapper.productOne(cart.getProductno()).getName();
			}
		}
		String itemName = cartNames[0] + " 그외" + (orderlist.size()-1);
		
		String order_id = "cafeweather";
		
		// request값 담기.
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("cid", "TC0ONETIME");
		parameters.add("tid", tid);
		parameters.add("partner_order_id", order_id); // 주문명
		parameters.add("partner_user_id", userid);
		parameters.add("pg_token", pgToken);
		
        // 하나의 map안에 header와 parameter값을 담아줌.
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
		
        // 외부url 통신
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/approve";
        // 보낼 외부 url, 요청 메시지(header,parameter), 처리후 값을 받아올 클래스. 
		kakopayApprove approveResponse = template.postForObject(url, requestEntity, kakopayApprove.class);

		return approveResponse;
	}

    // header() 셋팅
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK 87154e04907f2dc38861dfd027d3cee0");
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		return headers;
	}
}
