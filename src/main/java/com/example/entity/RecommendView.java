package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "RECOMMEND_VIEW")
@Subselect("" +
        "SELECT R.NO,\r\n" +
        "       R.REGDATE,\r\n" +
        "       R.CATEGORY,\r\n" +
        "       R.PRODUCTNO,\r\n" +
        "       P.CATEGORY AS PRODUCTCATEGORY,\r\n" +
        "       P.NAME AS PRODUCTNAME,\r\n" +
        "       P.PRICE,\r\n" +
        "       P.CONTENT\r\n" +
        "FROM RECOMMENDTBL R\r\n" +
        "         INNER JOIN PRODUCTTBL P\r\n"+ 
        " ON R.PRODUCTNO = P.NO")
public class RecommendView {
    
    @Id
    Long no;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    Date regdate = null;

    String category;

    Long productno;

    String productcategory;

    String productname;

    String content;

    Long price;

    @Transient
    String productimageurl;
}
