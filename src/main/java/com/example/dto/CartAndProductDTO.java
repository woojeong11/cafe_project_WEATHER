package com.example.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartAndProductDTO {
    Long no;
    Long quantity;
    String sessionid;
    Long productno;

    String name;
    String content;
    String category;
    Long price;
    Date regdate;
}
