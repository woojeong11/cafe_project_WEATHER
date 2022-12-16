package com.example.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.config.GlobalProperties;
import com.example.dto.ReviewDTO;
import com.example.dto.ReviewImageDTO;
import com.example.dto.ReviewScoreDTO;
import com.example.service.review.ReviewImageService;
import com.example.service.review.ReviewScoreService;
import com.example.service.review.ReviewService;

@RestController
@RequestMapping(value = "/rest/review")
public class ReviewRestController {
    
    @Autowired
    ReviewService reviewservice;

    @Autowired
    ReviewImageService reviewImageService;

    @Autowired
    ReviewScoreService reviewscoreservice;

    //사진 , 글 한번에 post
    //http://127.0.0.1:8085/QOOT3/rest/review/insert.json
    @PostMapping(value="/insert.json")
    public Map<String, Object> insertPost(
        @ModelAttribute ReviewDTO review,
        @RequestParam(name = "files", required = false) MultipartFile[] file, 
        HttpServletRequest request
        )throws IOException {
            
        Map<String, Object> map = new HashMap<>(); 
        
        review.setUserid(request.getAttribute("userid").toString());//userid 받아옴

        // review테이블의 no(시퀀스)를 변수 no에 담아 reviewimage 테이블에서 reviewno로 받을수있게 해준다.
        long no =reviewservice.reviewNumber();
        review.setNo(no);
        //review.setProductno(productDTO.getNo());

        System.out.println(no);
        System.out.println(review);
        reviewservice.insertReview(review);//글 저장
        
        if(file != null){
            List<ReviewImageDTO> list = new ArrayList<>();//사진을 리스트형식으로 받음
            for(int i=0; i<file.length; i++){
    
                ReviewImageDTO reviewImg = new ReviewImageDTO();
    
                reviewImg.setReviewno(no);
                reviewImg.setImagetype(file[i].getContentType());
                reviewImg.setImagename(file[i].getOriginalFilename());
                reviewImg.setImagesize(file[i].getSize());
                reviewImg.setImagedata(file[i].getBytes());
                
                list.add(reviewImg);
    
                reviewImageService.ReviewImageinsert(reviewImg);
            
            }
        }
        map.put("status", 200);
        return map;
    }

    //좋아요를 위한 post
    //127.0.0.1:8085/QOOT3/rest/review/like.json
    @PostMapping(value = "/like.json")
    public Map<String,Object>  reviewlikePost(
        HttpServletRequest request,//아이디를 받기위한 부분  
        @RequestParam(name="no")long no
    ) {
        Map<String,Object> map = new HashMap<>();

        ReviewScoreDTO reviewscore = new ReviewScoreDTO();
        reviewscore.setUserid(request.getAttribute("userid").toString());
        reviewscore.setReviewno(no);


        ReviewScoreDTO score = reviewscoreservice.ReviewFindlike(reviewscore);//좋아요가있는지 확인
        System.out.println(score+"====================좋아요가있는지 확인=============");

        if(score == null){
            System.out.println("====================insert좋아요"+reviewscore+"=============");
            reviewscoreservice.insertReviewLike(reviewscore);//없으면 인서트
            map.put("status", 200);
            map.put("result", "좋아요");
        }
        else if (score != null){
            System.out.println("====================="+reviewscore+"delete좋아요=====================");
            reviewscoreservice.Reciewlikesdelete(reviewscore);//있으면 딜리트
            map.put("status", 200);
            map.put("result", "좋아요해제");
        }

        return map;
    }

    // 좋아요 갯수
    // 127.0.0.1:8085/QOOT3/rest/review/countlike.json?no=
    @GetMapping(value = "/countlike.json")
    public Long countreviewlikeGET(  
        @RequestParam(name="no")long no
    ) {
        
        return reviewscoreservice.CountReviewScore(no);
    }

    // 이미지

    // 수정
    // http://127.0.0.1:8085/QOOT3/rest/review/update.json
    @PostMapping(value="/update.json")
    public Map<String,Object> updatePost(
        @ModelAttribute ReviewDTO review,
        @RequestParam(name = "files", required = false) MultipartFile[] file,
        HttpServletRequest request,
        @RequestParam(name="no")long no
    )throws IOException{
        
        Map<String,Object> map = new HashMap<>();

        ReviewDTO review1 =  reviewservice.selectOneReview(no);

        review1.setTitle(review.getTitle());
        review1.setContent(review.getContent());

        reviewservice.updateReview(review1);

        if(file != null){
            //reviewImageService.deleteReviewImage(no);

            List<ReviewImageDTO> list = new ArrayList<>();//사진을 리스트형식으로 받음
            for(int i=0; i<file.length; i++){

                ReviewImageDTO reviewImg = new ReviewImageDTO();

                reviewImg.setReviewno(no);
                reviewImg.setImagetype(file[i].getContentType());
                reviewImg.setImagename(file[i].getOriginalFilename());
                reviewImg.setImagesize(file[i].getSize());
                reviewImg.setImagedata(file[i].getBytes());
                
                list.add(reviewImg);

                if(file[i].getSize()!=0){
                    
                    reviewImageService.ReviewImageinsert(reviewImg);
                }

            }
        }
        map.put("status", 200);
        map.put("url", GlobalProperties.servepath + "/review/selectlist.do");
        
        return map;
    }

    // 사진 update 페이지에서 추가post
    //http://127.0.0.1:8085/QOOT3/rest/review/updateplusimage.json
    @PostMapping(value="/updateplusimage.json")
    public Map<String, Object> updateplusimagePost(
        @RequestParam(name = "files") MultipartFile[] file,
        @RequestParam(name = "no") Long no
        )throws IOException {
            
        Map<String, Object> map = new HashMap<>(); 
        
        List<ReviewImageDTO> list = new ArrayList<>();//사진을 리스트형식으로 받음
        for(int i=0; i<file.length; i++){

            ReviewImageDTO reviewImg = new ReviewImageDTO();

            reviewImg.setReviewno(no);
            reviewImg.setImagetype(file[i].getContentType());
            reviewImg.setImagename(file[i].getOriginalFilename());
            reviewImg.setImagesize(file[i].getSize());
            reviewImg.setImagedata(file[i].getBytes());
            
            list.add(reviewImg);

            reviewImageService.ReviewImageinsert(reviewImg);
        }

        map.put("status", 200);
        map.put("url", "http://3.38.209.149:8080" + GlobalProperties.servepath +"/review/update.do?no=" + no);
        return map;
    }


    // 사진 번호 목록주기
    //http://127.0.0.1:8085/QOOT3/rest/review/imagelist.json
    @GetMapping(value="/imagelist.json")
    public Map<String, Object> imagelistGET(
        @RequestParam(name = "reviewno") Long reviewno
    ){
            
        Map<String, Object> map = new HashMap<>(); 
        
        List<Long> reviewimage =reviewImageService.selectReviewImage(reviewno);
        
        map.put("list", reviewimage);

        return map;
    }

    // 사진삭제
    //http://127.0.0.1:8085/QOOT3/rest/review/imagedelete.json
    @DeleteMapping(value="/imagedelete.json")
    public Map<String, Object> imagedeleteDELETE(
        @RequestParam(name="no") Long no
    ){

        Map<String, Object> map = new HashMap<>(); 

        System.out.println(no + "no=============================================");

        int ret = reviewImageService.deleteimageone(no);
        if(ret == 1){
            map.put("status", 200);
            return map;
        }
        else{
            map.put("status", 0);
            return map;
        }
    }
}
