package com.example.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartDTO {
    Long no;
    Long quantity;
    String sessionid;
    Long productno;
    Date regdate;

    String category;
    String content;
    String name;
    String price;
 
    private String image;
    private Long SUM;
    private Long CNT;


}
