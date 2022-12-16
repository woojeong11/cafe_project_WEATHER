package com.example.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewDTO {

    private Long no;//리뷰 글 번호

    private String content;//리뷰내용

    private String title;//리뷰제목

    private Long hit = 1L;//조회수

    private Date regdate = null;

    private String userid;//유저아이디

    private long productno;//디저트번호

    private String[] image;//목록용 이미지변수

    private int chk=0;

    private Long count;

    // 리뷰 작성자 프로필 이미지 url
    private String profileurl;

    //닉네임
    private String Nickname;

    //상풍명
    private String name;

    //날짜
    private String regdate1;



}
