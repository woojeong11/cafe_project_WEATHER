package com.example.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.ReviewImageDTO;

@Mapper
public interface ReviewImageMapper {
    
    //이미지등록
    public int ReviewImageinsert(ReviewImageDTO reviewDTO);

    public List<Long> selectReviewImage(Long reviewno);

    //이미지 한개 확인용
    public ReviewImageDTO selectImageone(Long no);

    //이미지 수정
    public int updateReviewImage(ReviewImageDTO reviewimageDTO);

    //이미지 한개삭제
    public int deleteimageone (Long map);

    // 리뷰 no 기반 이미지 삭제
    public int deleteReviewImage(Long reviewno);

}
