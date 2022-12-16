package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductDTO;
import com.example.dto.ReviewDTO;
import com.example.dto.ReviewImageDTO;
import com.example.dto.ReviewScoreDTO;
import com.example.entity.Minfo;
import com.example.entity.PayResult;
import com.example.service.MemberService;
import com.example.service.Order1Service;
import com.example.service.review.ReviewImageService;
import com.example.service.review.ReviewScoreService;
import com.example.service.review.ReviewService;


@Controller
@RequestMapping(value="/review")
public class ReviewController {
    
    @Autowired
    ReviewService reviewservice;

    @Autowired
    ReviewImageService reviewImageService;

    @Autowired
    ResourceLoader resourceloader;

    @Autowired
    ReviewScoreService reviewscoreservice;

    @Autowired
    MemberService mService;

    @Autowired
    Order1Service oService;


    //등록 get
    @GetMapping(value="/insert.do")
    public String insertGET(
        Model model,
        HttpServletRequest request
    ){   
        
            
        Map<String, Object> map = new HashMap<>();
        map.put("userid", request.getAttribute("userid").toString());
        map.put("type", 2);      
        int count = oService.countforcustomer(map);
        map.put("start", 1);
        map.put("end", count);
        List<PayResult> list = oService.selectType2(map);

        model.addAttribute("list", list);

        return "Review/insert";
    }

    //사진 , 글 한번에 post
    //http://127.0.0.1:8085/QOOT3/review/insert.do
    @PostMapping(value="/insert.do")
    public String insertPost(
        @ModelAttribute ReviewDTO review,
        @ModelAttribute ProductDTO productDTO,
        @RequestParam(name = "imageee") MultipartFile[] file,
        Model model,
        HttpServletRequest request
        )throws IOException {
   
        review.setUserid(request.getAttribute("userid").toString());//userid 받아옴

        // review테이블의 no(시퀀스)를 변수 no에 담아 reviewimage 테이블에서 reviewno로 받을수있게 해준다.
        long no =reviewservice.reviewNumber();
        review.setNo(no);
        //review.setProductno(productDTO.getNo());

        System.out.println(no);

        reviewservice.insertReview(review);//글 저장
        
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

        return "redirect:/review/selectlist.do";
    }



    //유저로그인 X ===================================================================================================
    //127.0.0.1:8085/QOOT3/guestselectlist.do
    @GetMapping(value = "/guestselectlist.do")
    public String  guestselectlistGet(
        @RequestParam(name="page", defaultValue = "1")int page,
        HttpServletRequest request, HttpServletResponse response,
        Model model
    ) {
        HttpSession session = request.getSession(); 

        Map<String, Object> map = new HashMap<>();
        //페이지네이션 부분=
        map.put("start",(page*5)-4);
        map.put("end",page*5);

        long total = reviewservice.ReviewCount();//페이지네이션용 갯수 세어주기

        //글목록
        List<ReviewDTO> list = reviewservice.selectlistReview(map);

        //글목록(리스트형태) 의 변수 list 안에서 이미지도 반복
        for(ReviewDTO review : list){

            // 리뷰올린 사람 아이디
            Minfo minfo = mService.memberinfoselectone(review.getUserid());
            review.setProfileurl(minfo.getUrl());
            
            // 닉네임
            review.setNickname(mService.memberSelectone(review.getUserid()).getNickname());

            //이미지번호를 리스트형태로 받아옴
            List<Long> obj =reviewImageService.selectReviewImage(review.getNo());
            //이미지 담을 변수선언(배열형태)
            String[] images = new String[obj.size()]; 

            Long reviewcount = reviewscoreservice.CountReviewScore(review.getNo());//총 좋아요 갯수

            review.setCount(reviewcount);

            //이미지크기만큼 반복
            for(int i=0;i< obj.size();i++){

                images[i] = "/review/image?no="+obj.get(i);

            }
            review.setImage(images);

            if( session.getAttribute("LOGIN") !=  null){
                return "redirect:/review/selectlist.do";
            }
        }

        model.addAttribute("list", list);
        model.addAttribute("pages", (total-1)/5+1);

        return "Review/selectlist";
    }


    

    //유저로그인 O ===================================================================================================

    //페이지네이션 사진목록, 글목록, 좋아요
    //127.0.0.1:8085/QOOT3/selectlist.do
    @GetMapping(value = "/selectlist.do")
    public String  selectlistGet(
        @RequestParam(name="page", defaultValue = "1")int page,
        HttpServletRequest request, HttpServletResponse response,
        Model model
    ) {

        HttpSession session = request.getSession();

        Map<String, Object> map = new HashMap<>();
        
        //페이지네이션 부분=
        map.put("start",(page*5)-4);
        map.put("end",page*5);

        long total = reviewservice.ReviewCount();//페이지네이션용 갯수 세어주기

        //글목록
        List<ReviewDTO> list = reviewservice.selectlistReview(map);

        //글목록(리스트형태) 의 변수 list 안에서 이미지도 반복
        for(ReviewDTO review : list){

            // 리뷰올린 사람 아이디
            Minfo minfo = mService.memberinfoselectone(review.getUserid());
            review.setProfileurl(minfo.getUrl());
            
            // 닉네임
            review.setNickname(mService.memberSelectone(review.getUserid()).getNickname());
            
            //이미지번호를 리스트형태로 받아옴
            List<Long> obj =reviewImageService.selectReviewImage(review.getNo());

            //이미지 담을 변수선언(배열형태)
            String[] images = new String[obj.size()]; 

            Long reviewcount = reviewscoreservice.CountReviewScore(review.getNo());//총 좋아요 갯수

            review.setCount(reviewcount);

            //이미지크기만큼 반복
            for(int i=0;i< obj.size();i++){

                images[i] = "/review/image?no="+obj.get(i);

            }
            review.setImage(images);

            if( session.getAttribute("LOGIN") !=  null){
                // System.out.println("------------------------------------------1-----------------------------------------");
                ReviewScoreDTO obj1 = new ReviewScoreDTO();
                obj1.setReviewno(review.getNo());
                obj1.setUserid(request.getAttribute("userid").toString());

                ReviewScoreDTO reviewOne = reviewscoreservice.ReviewFindlike(obj1);

                if(reviewOne != null){
                    review.setChk(1);
                }
            }

        }
        model.addAttribute("list", list);
        model.addAttribute("pages", (total-1)/5+1);

        return "Review/selectlist";
    }


    //좋아요를 위한 post
    //127.0.0.1:8085/QOOT3/selectlist.do
    @PostMapping(value = "/selectlist.do")
    public String  selectlistPost(
        HttpServletRequest request,//아이디를 받기위한 부분
        // @RequestParam(name="type") int type,  
        @RequestParam(name="no")long no,  
        Model model
    ) {
        ReviewScoreDTO reviewscore = new ReviewScoreDTO();
        reviewscore.setUserid(request.getAttribute("userid").toString());
        reviewscore.setReviewno(no);


        ReviewScoreDTO score = reviewscoreservice.ReviewFindlike(reviewscore);//좋아요가있는지 확인
        System.out.println(score+"====================좋아요가있는지 확인=============");

        if(score == null){
            System.out.println("====================insert좋아요"+reviewscore+"=============");
            reviewscoreservice.insertReviewLike(reviewscore);//없으면 인서트
        }
        else if (score != null){
            System.out.println("====================="+reviewscore+"delete좋아요=====================");
            reviewscoreservice.Reciewlikesdelete(reviewscore);//있으면 딜리트
        }
        model.addAttribute("like", reviewscore);

        return "redirect:/review/selectlist.do";
    }





    //이미지url(이미지가 잘 오는지 웹에서 확인용)
    // http://127.0.0.1:8085/QOOT3/review/image?no=4
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> imageGET(
        @RequestParam(name="no")Long no) throws IOException{
            // System.out.println("no=========="+no);
            if(no > 0L) {           
                ReviewImageDTO item = reviewImageService.selectImageone(no);
                if(item.getImagesize() > 0L) {
                    // 타입설정 png인지 jpg인지 gif인지
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(
                        MediaType.parseMediaType( item.getImagetype() ) );
                    // 실제이미지데이터, 타입이포함된 header, status 200    
                    ResponseEntity<byte[]> response 
                        = new ResponseEntity<>(
                            item.getImagedata(), headers, HttpStatus.OK);
                    return response;        
                }
                else {
                    InputStream is = resourceloader.getResource("classpath:/static/img/images.png")
                        .getInputStream();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_PNG);
                    // 실제이미지데이터, 타입이포함된 header, status 200    
                    ResponseEntity<byte[]> response 
                        = new ResponseEntity<>(
                            is.readAllBytes(), headers, HttpStatus.OK);
                    return response;
                }
            }
            else {
                InputStream is = resourceloader.getResource("classpath:/static/img/images.png")
                        .getInputStream();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);
                // 실제이미지데이터, 타입이포함된 header, status 200    
                ResponseEntity<byte[]> response 
                    = new ResponseEntity<>(
                        is.readAllBytes(), headers, HttpStatus.OK);
                return response;
            }
        }

    
    //셀렉원에서 업데이트로 변경
    @GetMapping(value="/update.do")
    public String selectoneGET(
        ModelMap model,
        @RequestParam(name="no")long no//번호와 일치하는 사진과 글정보 받아옴
    ){

        ReviewDTO review = reviewservice.selectOneReview(no);
        model.addAttribute("review", review);

        model.addAttribute("no", no);

        return "Review/update";
    }


    @PostMapping(value="/update.do")
    public String updatePost(
        @ModelAttribute ReviewDTO reviewDTO,
        Model model,
        @RequestParam(name="no")long no
    )throws IOException{

        // System.out.println("aaaa");
        // System.out.println("==================================file");
        // System.out.println(reviewDTO.toString());

        // System.out.println(file[0].getOriginalFilename());
        // System.out.println("==================================file");
        
        ReviewDTO review =  reviewservice.selectOneReview(no);

        review.setTitle(reviewDTO.getTitle());
        review.setContent(reviewDTO.getContent());

        reviewservice.updateReview(review);
        
        return "redirect:/review/update.do?no="+reviewDTO.getNo();
    }

    // 이미지 수정 1개
    @PostMapping(value = "/updateimage.do")
    public String updateimagePOST(
        @RequestParam(name = "rawfile") MultipartFile file,
        @RequestParam(name = "rawreviewno") Long reviewno,
        @RequestParam(name = "rawno") Long no
    ) throws IOException{

        ReviewImageDTO reviewimage = new ReviewImageDTO();

        reviewimage.setNo(no);
        reviewimage.setImagename(file.getOriginalFilename());
        reviewimage.setImagedata(file.getBytes());
        reviewimage.setImagesize(file.getSize());
        reviewimage.setImagetype(file.getContentType());
        reviewimage.setReviewno(reviewno);

        reviewImageService.updateReviewImage(reviewimage);
        
        return "redirect:/review/update.do?no="+reviewno;
    }

    //이미지 한개 삭제
    @PostMapping(value = "/delete_imageone.do")
    public String deleteonePOST( 
        @RequestParam(name="no") Long no,
        @RequestParam(name="reviewno") Long reviewno
        ) {
            System.out.println(reviewno + "reviewno=============================================");
            System.out.println(no + "no=============================================");

            int ret = reviewImageService.deleteimageone(Long.valueOf(no));
            System.out.println("==============================================="+ret);

        return "redirect:/review/update.do?no="+reviewno; 
    }
    
    // 리뷰 삭제
    @PostMapping(value = "/delete.do")
    public String deleteReview(
        @RequestParam(name = "no") Long no
    ){

        reviewscoreservice.deletereviewscore(no);

        reviewImageService.deleteReviewImage(no);

        reviewservice.deleteReview(no);
        return "redirect:/review/selectlist.do";
    }
    
    
}
