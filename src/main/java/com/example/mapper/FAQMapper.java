package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.FAQDTO;

@Mapper//맵퍼로 꼭 선언 해주어야 함.
public interface FAQMapper {//여기서도 꼭 인터페이스로 변경해주기
    
    //등록
    public int insertFAQ (FAQDTO faqdto);

    //수정
    public int updateFAQ (FAQDTO faqdto);

    //삭제
    public int deleteFAQ (long no);

    //페이지네이션, 검색
    public List<FAQDTO> FAQSearchPagenationList (Map<String, Object> map);

    //페이지네이션, 검색(히트순 정렬)
    public List<FAQDTO> FAQSearchPagenationhitList (Map<String, Object> map);

    //페이지갯수
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

