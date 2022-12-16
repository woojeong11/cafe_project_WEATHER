package com.example.service.review;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dto.ReviewDTO;
import com.example.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    //리뷰등록
    @Override
    public int insertReview(ReviewDTO reviewDTO) {
        try {
            return reviewMapper.insertReview(reviewDTO);
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1;
        }
    }   

    //목록, 페이지네이션
    @Override
    public List<ReviewDTO> selectlistReview(Map<String, Object> map) {
        try {
            return reviewMapper.selectlistReview(map);
        } 
        catch (Exception e) {
           e.printStackTrace();
            return null;
        }
    }


    //페이지갯수
    @Override
    public long ReviewCount() {
        try {
            return reviewMapper.ReviewCount();
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1;
        }
    }

    //시퀀스
    @Override
    public long reviewNumber() {
        try {
            return reviewMapper.reviewNumber();
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1;
        }
    }


    //글 한개조회
    @Override
    public ReviewDTO selectOneReview(long no) {
        try {
            return reviewMapper.selectOneReview(no);
        } 
        catch (Exception e) {
           e.printStackTrace();
            return null;
        }
    }

    //글 수정
    @Override
    public int updateReview(ReviewDTO reviewDTO) {
        try {
            return reviewMapper.updateReview(reviewDTO);
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1;
        }
    }

    // 베스트 리뷰용
    @Override
    public List<Map<String, Object>> selectBest(Map<String, Object> map) {
        try {
            return reviewMapper.selectBest(map);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 나의 리뷰 리스트 목록
    @Override
    public List<ReviewDTO> selectlistmyreview(Map<String, Object> map) {
        try {
            return reviewMapper.selectlistmyreview(map);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 나의 리뷰 리스트 목록 갯수
    @Override
    public long myReviewCount(String userid) {
        try {
            return reviewMapper.myReviewCount(userid);
        } 
        catch (Exception e) {
           e.printStackTrace();
            return -1;
        }
    }


    //삭제
   @Override
   public int deleteReview(long no) {
       try {
           return reviewMapper.deleteReview(no);
       } 
       catch (Exception e) {
           e.printStackTrace();
           return -1;
       }
   }

}
