package com.example.dto;


import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewScoreDTO {

    private Long no;//좋아요 번호

    private Date regdate = null;

    private String userid;//유저아이디

    private long reviewno;//리뷰넘버
}
