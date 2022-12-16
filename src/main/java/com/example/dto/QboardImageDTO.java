package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QboardImageDTO {
    Long no = 0L;
    Long qboardno;
    String imagename;
    String imagetype;
    Long imagesize;
    byte[] imagedata;
    Date regdate;
}

