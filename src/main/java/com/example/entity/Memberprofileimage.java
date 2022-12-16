package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "PROFILEIMAGETBL")
@SequenceGenerator(name = "PROFILEIMAGESEQ", sequenceName = "SEQ_PROFILEIMAGE_NO", initialValue = 1, allocationSize = 1)
public class Memberprofileimage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILEIMAGESEQ")
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
    
    @OneToOne
    @JoinColumn(name = "USERID" , referencedColumnName = "USERID")
    private Member member;

    // 테이블 컬럼과 상관없는 임시변수
    @Transient
    String imageurl;
}
