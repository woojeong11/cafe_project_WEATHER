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
@Table(name = "PAYRESULT_VIEW")
@Subselect("" +
        "SELECT O.*,\n" +
        "       P.NAME AS PRODUCTNAME\n" +
        "FROM ORDERTBL1 O\n" +
        "         INNER JOIN PRODUCTTBL P ON O.PRODUCTNO = P.NO\n")
public class PayResult {
    
    @Id
    Long no;

    
    String name;
    String userid;
    
    Long productno;
    Long price;
    Long quantity;
    
    String msg;
    String productname;
    Long type;
    Long tprice;
    String paycode;
    Long addressno;

    
    String regdate = null;
    
    @Transient
    String productimageurl;
}
