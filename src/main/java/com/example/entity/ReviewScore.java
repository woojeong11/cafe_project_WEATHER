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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "REVIEWSCORETBL")
@SequenceGenerator(name = "REVIEWSCORESEQ", sequenceName = "SEQ_REVIEWSCORE_NO", initialValue = 1, allocationSize = 1)
public class ReviewScore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEWSCORESEQ")
    Long no;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    Date regdate = null;

    @ToString.Exclude
    @JsonBackReference(value = "member8")
    @ManyToOne
    @JoinColumn(name = "userid")
    private Member member;

    @ToString.Exclude
    @JsonBackReference(value = "review1")
    @ManyToOne
    @JoinColumn(name = "reviewno")
    private Review review;

}
