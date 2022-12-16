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
public class ReviewImageDTO {
    
    private Long no; // 이미지번호
    
    private String imagename;

    private Long imagesize;

    private String imagetype;

    private byte[] imagedata;

    private Date regdate;

    private long reviewno;

    
}
