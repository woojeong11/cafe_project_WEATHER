package com.example.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.RecommendView;

@Repository
@Transactional
public interface RecommendViewRepository extends JpaRepository<RecommendView, Long> {
    
    // 랜덤 추출 ( 추천용0 )
    @Query(value = "SELECT * FROM RECOMMEND_VIEW where CATEGORY = ?1 AND PRODUCTCATEGORY = ?2 order by RAND() limit 1",nativeQuery = true)
    RecommendView findRANDOM(String category, String productcategory);
}
