package com.example.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "REVIEWTBL")
@SequenceGenerator(name = "REVIEWSEQ", sequenceName = "SEQ_REVIEW_NO", initialValue = 1, allocationSize = 1)
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEWSEQ")
    Long no;

    @Column(length = 50)
    String title;

    @Lob
    String content;

    Long hit = 1L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    Date regdate = null;
    
    // 막히면 안 되는 쪽
    @JsonManagedReference(value = "review1")
    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
    @OrderBy(value = "no desc")
    private List<ReviewScore> reviewscore;

    // 막히면 안 되는 쪽
    @ToString.Exclude
    @JsonManagedReference(value = "review2")
    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
    @OrderBy(value = "no desc")
    private List<ReviewImage> reviewImage;

    @ToString.Exclude
    @JsonBackReference(value = "member4")
    @ManyToOne
    @JoinColumn(name = "userid")
    private Member member;

    @ToString.Exclude
    @JsonBackReference(value = "product3")
    @ManyToOne
    @JoinColumn(name = "productno")
    private Product product;

}
