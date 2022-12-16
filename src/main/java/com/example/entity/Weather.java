package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "WEATHERTBL")
public class Weather {

    @Id
    Long no; // 기본키
    
    String baseDate;
    String fcstTime;
    String fcstValue;
    Long nx;
    Long ny;
    String category;
    String baseTime;
    String fcstDate;

}
