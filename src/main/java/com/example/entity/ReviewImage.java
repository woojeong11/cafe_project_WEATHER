package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "REVIEWIMAGETBL")
@SequenceGenerator(name = "REVIEWIMAGESEQ", sequenceName = "SEQ_REVIEWIMAGE_NO ", initialValue = 1, allocationSize = 1)
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEWIMAGESEQ")
    Long no;

    String imagename;

    Long imagesize;

    String imagetype;
    
    @Lob
    byte[] imagedata;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;
    
    // 외래키 (다른 엔티티의 객체) 답글(n)  <====> 게시글(1)
    // 막히는 쪽
    @JsonBackReference(value = "review2")
    @ManyToOne
    @JoinColumn(name = "reviewno")
    private Review review;

    // 테이블 컬럼과 상관없는 임시변수
    @Transient
    String imageurl;
}
