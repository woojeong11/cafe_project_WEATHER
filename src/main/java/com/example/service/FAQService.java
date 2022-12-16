package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.FAQDTO;

@Service//서비스 꼭 붙여주기
public interface FAQService {//interface로 꼭 변경
    
    //등록
    public int insertFAQ(FAQDTO faqdto);

    //수정
    public int updateFAQ (FAQDTO faqdto);

    //삭제
    public int deleteFAQ (long no);

    //페이지네이션, 검색
    public List<FAQDTO> FAQSearchPagenationList (Map<String, Object> map);

    //페이지네이션, 검색(히트순 정렬)
    public List<FAQDTO> FAQSearchPagenationhitList (Map<String, Object> map);

    //페이지네이션을 위한 갯수
    public long FAQcount(String text);

    //한개조회
    public FAQDTO FAQselectOne(long no);

    //조회수
    public long updatedHitFAQ(long no);

    //조회수순 이전글
    public long prevpageFAQhit(long no);

    //조회수순 다음글
    public long nextpageFAQhit(long no);

    //이전글(최신순)
    public long prevpageFAQno(long no);

    //다음글(최신순)
    public long nextpageFAQno(long no);
}