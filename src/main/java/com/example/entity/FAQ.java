package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "FAQTBL")
@SequenceGenerator(name = "FAQSEQ", sequenceName = "SEQ_FAQ_NO", initialValue = 1, allocationSize = 1)
public class FAQ {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAQSEQ")
    Long no;

    @Column(length = 50)
    String title;

    @Column(length = 500)
    String content;

    Long hit=0L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    Date regdate = null;
}
