package com.example.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.entity.Recommend;
import com.example.entity.RecommendView;

@Service
public interface RecommendService {
    
    // 등록
    public int recommendinsert( Recommend recommend);

    // category(날씨)기반 조회
    public List<Recommend> recommendselectcate( String code, int page, int count );

    // 삭제(체크한 것 1개 or 일괄)
    public int deleterecommend( List<Long> chk );

    // 랜덤하게 1개 추출( 추천용 )
    public RecommendView selectrandom(String category, String productcategory);

    // category 기반 총 개수
    public int countBycategory( String code);

}
