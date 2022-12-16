package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.CartDTO;
import com.example.dto.ProductDTO;
import com.example.mapper.CartMapper;

@Service
public class CartServiceImpl implements CartService{

    @Autowired CartMapper cMapper;

    @Override
    public int inserCart(CartDTO cart) {
        try{
            cMapper.inserCart(cart);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<CartDTO> selectList(String sessionid) {
        try{
            List<CartDTO> list = cMapper.selectList(sessionid);
            
            return list;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductDTO> selectProduct(String sessionid) {
        try{
            List<ProductDTO> list = cMapper.selectProduct(sessionid);
            
            return list;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CartDTO> selectDesertandDrink(String sessionid) {
        try{
            List<CartDTO> list = cMapper.selectDesertandDrink(sessionid);
            
            return list;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CartDTO> selectGoods(String sessionid) {
        try{
            List<CartDTO> list = cMapper.selectGoods(sessionid);
            
            return list;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public CartDTO selectone(Long no) {
        try{
            return cMapper.selectone(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CartDTO> selectall() {
        try{
            return cMapper.selectall();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public int deleteCartBatch(List<Long> list) {
        try{
            int result = cMapper.deleteCartBatch(list);
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteall() {
        try{
            int result = cMapper.deleteall();
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int modifyCount(List<CartDTO> list) {
        try{
            int result = cMapper.modifyCount(list);
            
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int upsertCart(CartDTO cart) {
        try{
            int result = cMapper.upsertCart(cart);
            
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteCart(String sessionid) {
        try{
            int result = cMapper.deleteCart(sessionid);
            
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteone(Map<String, Object> map) {
        try{
            int result = cMapper.deleteone(map);
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int countcart(String sessionid) {
        try{
            int result = cMapper.countcart(sessionid);
            
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

   
    
}
