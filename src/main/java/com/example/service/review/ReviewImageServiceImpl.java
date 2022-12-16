package com.example.service.review;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ReviewImageDTO;
import com.example.mapper.ReviewImageMapper;

@Service
public class ReviewImageServiceImpl implements ReviewImageService {


    @Autowired
    ReviewImageMapper RImapper;

    //이미지, 글 등록
    @Override
    public int ReviewImageinsert(ReviewImageDTO reviewDTO) {
        try {

            RImapper.ReviewImageinsert(reviewDTO);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //이미지 받기
    @Override
    public List<Long> selectReviewImage(Long reviewno) {
        try {

            return RImapper.selectReviewImage(reviewno);

        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ReviewImageDTO selectImageone(Long no) {
        try {

            return RImapper.selectImageone(no);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //수정
    @Override
    public int updateReviewImage(ReviewImageDTO reviewimageDTO) {
        try {

            return RImapper.updateReviewImage(reviewimageDTO);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //삭제
    @Override
    public int deleteReviewImage(long reviewno) {
        try {

            return RImapper.deleteReviewImage(reviewno);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //한개만 삭제
    @Override
    public int deleteimageone(Long map) {
        try {

            return RImapper.deleteimageone(map);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
   
}
