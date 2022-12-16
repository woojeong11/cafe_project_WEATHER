package com.example.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Recommend;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    
    // 카테고리 기반 조회
    public List<Recommend> findBycategory_codeContainingOrderByNoDesc(String code, PageRequest pageable);

    // 카테고리 기반 개수
    public int countBycategory_codeContaining( String code);

}
