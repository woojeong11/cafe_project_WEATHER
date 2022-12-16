package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ProductImageDTO;
import com.example.mapper.ProductImageMapper;
@Service
public class ProductImageServiceImpl implements ProductImageService{
    @Autowired ProductImageMapper iMapper;
    @Override
    public int insertImage(ProductImageDTO image) {
        try{
            iMapper.insertImage(image);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    @Override
    public List<ProductImageDTO> selectimageList(Long no) {
        try{
            return iMapper.selectimageList(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Long selectImageNoOne(Long productno) {
        try{
            return iMapper.selectImageNoOne(productno);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1L;
        }
    }



    @Override
    public ProductImageDTO selectImageNoOne2(Long productno) {
        try{
            return iMapper.selectImageNoOne2(productno);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public ProductImageDTO selectImageOne(Long no) {
        try{
            return iMapper.selectImageOne(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public int deleteImageOne(Long no) {
        try{
            return iMapper.deleteImageOne(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    @Override
    public int deleteImageBatch(Long productno) {
        try{
            return iMapper.deleteImageBatch(productno);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    //이미지 src사용(productno 기반)
    @Override
    public ProductImageDTO selectImageOneByproductno(Long no) {
        try{
            return iMapper.selectImageOneByproductno(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
