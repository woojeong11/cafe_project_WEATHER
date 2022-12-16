package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import com.example.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired ProductMapper pMapper;

    @Override
    public int insertProduct(ProductDTO pro) {
        try{
            pro.setRegdate(new Date());
            pMapper.insertProduct(pro);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Product> selectList() {
        try{
            return pMapper.selectList();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteProduct(Long no) {
        try{
            Integer result = pMapper.deleteProduct(no);
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Product productOne(Long no) {
        try{
            return pMapper.productOne(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductDTO> selectCategoryList(Map<String, Object> map) {
        try{
            return pMapper.selectCategoryList(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductDTO> selectListt() {
        try{
            return pMapper.selectListt();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductDTO selectNextandPre(Long no) {
        try{
            return pMapper.selectNextandPre(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long prevpageFAQ(Long no) {
        try{
            
            return pMapper.prevpageFAQ(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long nextpageFAQ(Long no) {
        try{

            return pMapper.nextpageFAQ(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductDTO> randomProduct() {
        try{
            List<ProductDTO> randomlist = pMapper.randomProduct();
            return randomlist;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long countList(String category) {
        try{
            Long result = pMapper.countList(category);
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductDTO> selectCategory(String category) {
        try{
            return pMapper.selectCategory(category);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long productSeq() {
        try{
            return pMapper.productSeq();
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ProductDTO> mainRandomProduct(Map<String, Object> map) {
        try{
            return pMapper.mainRandomProduct(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductDTO> mainSoldProduct(Map<String, Object> map) {
        try{
            return pMapper.mainSoldProduct(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

   
    
}
