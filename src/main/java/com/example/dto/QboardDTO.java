package com.example.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QboardDTO {
    Long no;
    String content;
    String title;
    String userid;
    Date regdate;
    String regdate1;

    private String image;
    String password;

    Long grp;
    Long hit;

    Long rown;
    String originuserid;

    String nickname;
}
