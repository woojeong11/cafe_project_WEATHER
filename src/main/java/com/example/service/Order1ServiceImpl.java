package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.Order1DTO;
import com.example.entity.PayResult;
import com.example.mapper.Order1Mapper;

@Service
public class Order1ServiceImpl implements Order1Service{
    @Autowired Order1Mapper oMapper;

    @Override
    public int insertOrderBatch(List<Order1DTO> list) {
        try{
            oMapper.insertOrderBatch(list);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // 조회(페이용)
    @Override
    public List<Order1DTO> selectOrderList(Map<String, Object> map) {
        try{
            return oMapper.selectOrderList(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 결제완료시 수정
    @Override
    public int updateList(Map<String, Object> map) {
        try{
            oMapper.updateList(map);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // 배달 진행중인 것들
    @Override
    public List<PayResult> selectType2(Map<String, Object> map) {
        try{
            return oMapper.selectType2(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 모든게 완료된 것들
    @Override
    public List<PayResult> selectType0(Map<String, Object> map) {
        try{
            return oMapper.selectType0(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 주문한 것 SELECTONE
    @Override
    public PayResult selectoneOrder(Map<String, Object> map) {
        try{
            return oMapper.selectoneOrder(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 관리자용 배달 진행중인 건들 조회(결제는 완료된 상태)
    @Override
    public List<PayResult> selectType2foradmin(Map<String, Object> map) {
        try{
            return oMapper.selectType2foradmin(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 관리자용 주문 완료건들조회(배달까지 완료된 상태)
    @Override
    public List<PayResult> selectType0foradmin(Map<String, Object> map) {
        try{
            return oMapper.selectType0foradmin(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 카운트 (갯수) 넣는 TYPE 번호에 따라 줌 ( CUSTOMER용 )
    @Override
    public int countforcustomer(Map<String, Object> map) {
        try{
            return oMapper.countforcustomer(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // 결제 실패나 결제취소시에 
    @Override
    public int deleteorder(String userid) {
        try{
            return oMapper.deleteorder(userid);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // 배달 진행중인 건들 조회(결제는 완료된 상태)(결제마다의 구역 나눔용)
    @Override
    public List<PayResult> selectType2ByPaycode(Map<String, Object> map) {
        try{
            return oMapper.selectType2ByPaycode(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int cancelorder(String code) {
        try{
            return oMapper.cancelorder(code);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    
}
