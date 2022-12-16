package com.example.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.CartDTO;
import com.example.dto.ProductDTO;

@Service

public interface CartService {

    //카트추가
    public int inserCart(CartDTO cart);
    //카트정보를 추가 또는 수정
    public int upsertCart(CartDTO cart);
    
    public List<CartDTO> selectList(String sessionid);
    
    public List<ProductDTO> selectProduct(String sessionid);
    public List<CartDTO> selectDesertandDrink(String sessionid);
    public List<CartDTO> selectGoods(String sessionid);
    
    //카트수량수정
    public int modifyCount(List<CartDTO> list);
    //selectone
    public CartDTO selectone(Long no);
    //카트리스트
    public List<CartDTO> selectall();
    //장바구니항목 1개삭제
    public int deleteone(Map<String, Object> map);
    //장바구니항목 일괄삭제
    public int deleteCartBatch(List<Long> list);
    //주문 후에 장바구니 비우기
    public int deleteCart(String sessionid);
    //장바구니 비우기
    public int deleteall();

    // 갯수
    public int countcart(String sessionid);

}
