package com.example.service.review;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.ReviewDTO;

@Service
public interface ReviewService {
    
    //등록
    public int insertReview(ReviewDTO reviewDTO);

    //페이지네이션으로 목록
    public List<ReviewDTO> selectlistReview(Map<String, Object> map);

    //페이지갯수
    public long ReviewCount();

    //리뷰시퀀스값
    public long reviewNumber();

    //글 한개조회
    public ReviewDTO selectOneReview(long no);
    
    // 베스트 리뷰용 조회(3개)
    public List<Map<String,Object>> selectBest(Map<String, Object> map);

    //글 수정
    public int updateReview(ReviewDTO reviewDTO);

    // 내가 작성한 리뷰 목록
    public List<ReviewDTO> selectlistmyreview( Map<String, Object> map);

    // 내가 작성한 리뷰 목록 페이지갯수
    public long myReviewCount(String userid);

    //글 삭제 
    public int deleteReview(long no);

}
