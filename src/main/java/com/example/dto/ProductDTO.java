package com.example.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {
    Long no;
    String name;
    String content;
    String category;
    Long price;
    Date regdate;
    Long productno;

    private String image;
    
    private String next_name;
    private String pre_name;
    private long next_no;
    private long pre_no;
    private long nextno;
    
    private long quantity;



}
