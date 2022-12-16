package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "RECOMMENDTBL")
@SequenceGenerator(name = "RECOMMENDSEQ", sequenceName = "SEQ_RECOMMEND_NO", initialValue = 1, allocationSize = 1)
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECOMMENDSEQ")
    Long no; // 기본키

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    Date regdate = null;

    // 테이블 컬럼과 상관없는 임시변수
    @Transient
    String productimageurl;

    // @ToString.Exclude
    @JsonBackReference(value = "product5")
    @ManyToOne
    @JoinColumn(name = "productno")
    private Product product;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "category")
    private WeatherCategory category;
}
