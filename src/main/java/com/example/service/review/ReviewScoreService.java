package com.example.service.review;

import org.springframework.stereotype.Service;

import com.example.dto.ReviewScoreDTO;

@Service
public interface ReviewScoreService {
    
    //좋아요등록
    public int insertReviewLike(ReviewScoreDTO reviewScoreDTO);

    //좋아요갯수
    public Long CountReviewScore(long reviewno);
    
    //좋아요삭제
    public int Reciewlikesdelete(ReviewScoreDTO reviewScoreDTO);

    //좋아요가 있는지 확인
    public ReviewScoreDTO ReviewFindlike(ReviewScoreDTO reviewScoreDTO);

    //리뷰no기반 좋아요 삭제
    public int deletereviewscore(Long reviewno);
}
