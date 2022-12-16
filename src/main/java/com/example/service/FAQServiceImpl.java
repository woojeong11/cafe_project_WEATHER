package com.example.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.FAQDTO;
import com.example.mapper.FAQMapper;

@Service//Impl도 무조건 서비스 어노테이션 선언하기
public class FAQServiceImpl implements FAQService{//서비스를 불러와 상속(implements)
    
    //오토와이드로 맵퍼 불러오기
    @Autowired
    FAQMapper faqmapper;


    //등록
    @Override
    public int insertFAQ(FAQDTO faqdto) {
        try {
            faqmapper.insertFAQ(faqdto);
            return 1;
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1;
        }
    }

    //검색 페이지네이션
    @Override
    public List<FAQDTO> FAQSearchPagenationList(Map<String, Object> map) {
        try {
            return faqmapper.FAQSearchPagenationList(map);
        } 
        catch (Exception e) {
           e.printStackTrace();
            return null;
        }
    }


    //갯수조회
    @Override
    public long FAQcount(String text) {
        try{
            return faqmapper.FAQcount(text);
        }

        catch(Exception e){
            e.getStackTrace();
            return 0L;
        }
    }

    //1개조회
    @Override
    public FAQDTO FAQselectOne(long no) {
        try{
            return faqmapper.FAQselectOne(no);

        }

        catch(Exception e){
            e.getStackTrace();
            return null;
        }
    }

    //조회수 증가
    @Override
    public long updatedHitFAQ(long no) {
        try{
            return faqmapper.updatedHitFAQ(no);

        }

        catch(Exception e){
            e.getStackTrace();
            return 0L;
        }

    }


    //수정
    @Override
    public int updateFAQ(FAQDTO faqdto) {
        try {
            return faqmapper.updateFAQ(faqdto);
            
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1;
        }
    }

    // 삭제
    @Override
    public int deleteFAQ(long no) {
        try {
            return faqmapper.deleteFAQ(no);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //조회수순 페이지네이션
    @Override
    public List<FAQDTO> FAQSearchPagenationhitList(Map<String, Object> map) {
        try {
            return faqmapper.FAQSearchPagenationhitList(map);
            
        } 
        catch (Exception e) {
           e.printStackTrace();
            return null;
        }
    }

    //이전글(조회수순)
    @Override
    public long prevpageFAQhit(long no) {
        try {
            return faqmapper.prevpageFAQhit(no);
            
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1L;
        }
    }

    //다음글(조회수순)
    @Override
    public long nextpageFAQhit(long no) {
        try {
            return faqmapper.nextpageFAQhit(no);
            
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1L;
        }
    }

    //이전글(최신순)
    @Override
    public long prevpageFAQno(long no) {
        try {
            return faqmapper.prevpageFAQno(no);
            
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1L;
        }
    }

    //다음글(최신순)
    @Override
    public long nextpageFAQno(long no) {
        try {
            return faqmapper.nextpageFAQno(no);
            
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1L;
        }
    }
    
}