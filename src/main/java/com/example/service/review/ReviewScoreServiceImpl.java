package com.example.service.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ReviewScoreDTO;
import com.example.mapper.ReviewScoreMapper;

@Service
public class ReviewScoreServiceImpl implements ReviewScoreService {

    @Autowired
    ReviewScoreMapper reviewscoremapper;

    // 좋아요 등록
    @Override
    public int insertReviewLike(ReviewScoreDTO reviewScoreDTO) {
        try {

            reviewscoremapper.insertReviewLike(reviewScoreDTO);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //총 좋아요 갯수
    @Override
    public Long CountReviewScore(long reviewno){
        try {
            return reviewscoremapper.CountReviewScore(reviewno);
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    //좋아요 삭제
    @Override
    public int Reciewlikesdelete(ReviewScoreDTO reviewScoreDTO) {
        try {
            return reviewscoremapper.Reciewlikesdelete(reviewScoreDTO);
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //좋아요가 있는지 확인
    @Override
    public ReviewScoreDTO ReviewFindlike(ReviewScoreDTO reviewScoreDTO) {
        try {
            return reviewscoremapper.ReviewFindlike(reviewScoreDTO);
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deletereviewscore(Long reviewno) {
        try {
            return reviewscoremapper.deletereviewscore(reviewno);
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }



   
}
