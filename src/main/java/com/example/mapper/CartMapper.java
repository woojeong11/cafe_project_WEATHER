package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.CartDTO;
import com.example.dto.ProductDTO;

@Mapper
public interface CartMapper {
    //카트넣기
    public int inserCart(CartDTO cart);
    //카트정보를 추가 또는 수정
    public int upsertCart(CartDTO cart);
    //세션아이디별 리스트
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

    //장바구니 비우기
    public int deleteall();
    
    //주문 후에 장바구니 비우기
    public int deleteCart(String sessionid);

    // 갯수
    public int countcart(String sessionid);
}