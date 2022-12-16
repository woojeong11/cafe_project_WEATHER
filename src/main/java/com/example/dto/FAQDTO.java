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
//=========자주묻는질문게시판=====================
public class FAQDTO {

    private Long no;
    private String title;
    private String content ;
    private long hit ;
    private Date regdate;

    private String regdate1;

    private long rown;
}
