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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "QBOARDTBL")
@SequenceGenerator(name = "QBOARDSEQ", sequenceName = "SEQ_QBOARD_NO", initialValue = 100001, allocationSize = 1)
public class Qboard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QBOARDSEQ")
    Long no;

    @Column(length = 100)
    String title;

    @Column(length = 300)
    String content;

    @Column(length = 15)
    String originuserid;

    Long grp;

    Long hit;

    String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    Date regdate = null;

    @ToString.Exclude
    @JsonBackReference(value = "member6")
    @ManyToOne
    @JoinColumn(name = "userid")
    private Member member;

    // 막히면 안 되는 쪽
    @JsonManagedReference(value = "qboard")
    @OneToMany(mappedBy = "qboard", cascade = CascadeType.REMOVE)
    private List<QboardImage> qboardimage;
}
