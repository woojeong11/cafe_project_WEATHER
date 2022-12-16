package com.example.service.qboard;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.QboardDTO;
import com.example.mapper.QboardMapper;

@Service
public class QboardServiceImpl implements QboardService{
    @Autowired QboardMapper qMapper;

    @Override
    public int insertQboard(QboardDTO board) {
        try{
            qMapper.insertQboard(board);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<QboardDTO> selectList() {
        try{
            return qMapper.selectList();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public QboardDTO selectOne(Long no) {
        try{
            return qMapper.selectOne(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long qboardSeq() {
        try{
            return qMapper.qboardSeq();
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insertRep(QboardDTO board) {
        try{
            return qMapper.insertRep(board);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Long countBoardListLikePagenation(Map<String, Object> map) {
        try{
            return qMapper.countBoardListLikePagenation(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1L;
        }
    }

    @Override
    public List<QboardDTO> qboardList(Map<String, Object> map) {
        try{
            return qMapper.qboardList(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long updatedHit(long no) {
        try{
            return qMapper.updatedHit(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateQboard(QboardDTO board) {
        try{
            return qMapper.updateQboard(board);
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteQboard(Long grp) {
        try{
            return qMapper.deleteQboard(grp);
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    
}
