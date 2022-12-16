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
@Table(name = "CONTACTUSTBL")
@SequenceGenerator(name = "CONTACTUSSEQ", sequenceName = "SEQ_CONTACTUS_NO", initialValue = 1, allocationSize = 1)
public class Contactus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACTUSSEQ")
    Long no;

    @Column(length = 20)
    String phone;

    @Column(length = 200)
    String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    Date regdate = null;

}
