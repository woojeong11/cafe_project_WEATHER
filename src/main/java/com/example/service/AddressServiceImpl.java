package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.AddressDTO;
import com.example.mapper.AddressMapper;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired AddressMapper aMapper;

    @Override
    public int insertAddress(AddressDTO address) {
        try{
            aMapper.insertAddress(address);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<AddressDTO> selectList(String userid) {
        try{
            return aMapper.selectList(userid);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AddressDTO selectRownum(String userid) {
        try{

            if(aMapper.selectRownum(userid) != null){
                return aMapper.selectRownum(userid);
            }
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long addressSeq() {
        try{
            return aMapper.addressSeq();
        }
        catch(Exception e){
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public int insertAddress2(AddressDTO address) {
        try{
            return aMapper.insertAddress2(address);
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    // 주소 조회(ADDRESSNO 기준)
    @Override
    public AddressDTO selectaddressByno(Long addressno) {
        try{
            return aMapper.selectaddressByno(addressno);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AddressDTO selectaddressBymap(Map<String, Object> map) {
        try{
            return aMapper.selectaddressBymap(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AddressDTO> selectListpage(Map<String, Object> map) {
        try{
            return aMapper.selectListpage(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 갯수(By USERID)
    @Override
    public int countaddresstbl(String userid) {
        try{
            return aMapper.countaddresstbl(userid);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updaterep(Map<String, Object> map) {
        try{
            return aMapper.updaterep(map);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // 삭제
    @Override
    public int deleteaddress(Long no) {
        try{
            return aMapper.deleteaddress(no);
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    
}
