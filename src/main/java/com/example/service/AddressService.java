package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.AddressDTO;

@Service
public interface AddressService {
    // 시퀀스 꺼내기
    public Long addressSeq();

    //주소저장 (꺼낸시퀀스사용)
    public int insertAddress(AddressDTO address);


    //주소저장 일반
    public int insertAddress2(AddressDTO address);

    //주소목록
    public List<AddressDTO> selectList(String userid);
    
    //주소1개불러오기(아이디별 대표 설정 배송지)
    public AddressDTO selectRownum(String userid);

    //주소1개불러오기(addressno 기준)
    public AddressDTO selectaddressByno(Long addressno);

    //주소1개불러오기(map 기준)
    public AddressDTO selectaddressBymap(Map<String, Object> map); 

    // 주소목록(페이지 네이션)
    public List<AddressDTO> selectListpage(Map<String, Object> map);

    // 갯수(By USERID)
    public int countaddresstbl(String userid);

    //주소 대표지 설정
    public int updaterep(Map<String, Object> map); 

    // 삭제
    public int deleteaddress(Long no);
}
