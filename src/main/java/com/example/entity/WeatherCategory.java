package com.example.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "WEATHERCATEGORYTBL")
public class WeatherCategory {
    
    @Id
    String code;

    @Column(name="weathercategory")
    String category;

    String imagename;

    Long imagesize;

    String imagetype;
    
    @ToString.Exclude
    @Lob
    byte[] imagedata;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;
    
    // 테이블 컬럼과 상관없는 임시변수
    @Transient
    String imageurl;

    // 막히면 안 되는 쪽
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Recommend> recommend;
}
