package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.entity.Recommend;
import com.example.entity.RecommendView;
import com.example.repository.RecommendRepository;
import com.example.repository.RecommendViewRepository;

@Service
public class RecommendServiceImpl implements RecommendService {
    
    @Autowired
    RecommendRepository recommendRepository;

    @Autowired
    RecommendViewRepository rvRepository;

    // 등록
    @Override
    public int recommendinsert(Recommend recommend) {
        try {
            recommendRepository.save(recommend);
            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // category(날씨)기반 조회
    @Override
    public List<Recommend> recommendselectcate(String code, int page, int count) {
        try {
            
            // 페이지 네이션 설정( 0부터 )
            PageRequest pageRequest = PageRequest.of(page-1, 5);

            List<Recommend> list = recommendRepository.findBycategory_codeContainingOrderByNoDesc(code, pageRequest);
            if( list != null){
                return list;
            }
            return null;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 체크한 것 삭제
    @Override
    public int deleterecommend(List<Long> chk) {
        try {
            recommendRepository.deleteAllByIdInBatch(chk);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 랜덤 추출
    @Override
    public RecommendView selectrandom(String category, String productcategory) {
        try {
            return rvRepository.findRANDOM(category, productcategory);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 카테고리기반 총 개수
    @Override
    public int countBycategory(String code) {
        try {
            return recommendRepository.countBycategory_codeContaining(code);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
