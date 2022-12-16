package com.example.service.qboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.QboardImageDTO;
import com.example.mapper.QboardImageMapper;

@Service
public class QboardImageServiceImpl implements QboardImageService{
    @Autowired QboardImageMapper iMapper;

    @Override
    public int insertImage(QboardImageDTO image) {
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
    public List<QboardImageDTO> selectimageList(Long no) {
        try{
            return iMapper.selectimageList(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public QboardImageDTO selectImageOne(Long no) {
        try{
            return iMapper.selectImageOne(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteQboardImage(Long no) {
        try{
            return iMapper.deleteQboardImage(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
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
    
}
