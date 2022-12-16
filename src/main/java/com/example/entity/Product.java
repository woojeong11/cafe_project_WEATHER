package com.example.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "PRODUCTTBL")
@SequenceGenerator(name = "PRODUCTSEQ", sequenceName = "SEQ_PRODUCT_NO", initialValue = 100001, allocationSize = 1)
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTSEQ")
    Long no;
    
    @Column(length = 30)
    String name;

    @Column(length = 100)
    String content;

    Long price;

    @Column(length = 10)
    String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    Date regdate = null;

    // 막히면 안 되는 쪽
    @ToString.Exclude
    @JsonManagedReference(value = "product1")
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<Cart> Cart;

    // 막히면 안 되는 쪽
    @ToString.Exclude
    @JsonManagedReference(value = "product3")
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<Review> review;

    // 막히면 안 되는 쪽
    @ToString.Exclude   
    @JsonManagedReference(value = "product4")
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<ProductImage> productImage;

    // 막히면 안 되는 쪽
    @ToString.Exclude
    @JsonManagedReference(value = "product5")
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<Recommend> recommend;

    // 막히면 안 되는 쪽
    @JsonManagedReference(value = "product6")
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<Order1> Order1;


}
