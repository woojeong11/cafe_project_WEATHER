package com.example.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "MEMBERTBL")
public class Member {
    @Id
    @Column(length = 100)
    String userid;
    
    @Column(length = 500)
    String userpw;

    @Column(length = 20)
    String phone;

    @Column(length = 30)
    String nickname;

    @Column(length = 10)
    String name;

    String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    Date regdate = null; //답글 작성일

    @Column(length = 15)
    String role="CUSTOMER";

    // 1은 정상회원, 0은 차단회원
    @Column(length = 1)
    int block=1;

    // 1이 일반회원, 0은 api 회원
    int type = 1;

    // 막히면 안 되는 쪽
    @JsonManagedReference(value = "member1")
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<Address> address;

    // 막히면 안 되는 쪽
    @JsonManagedReference(value = "member3")
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<Order1> order1;

    // 막히면 안 되는 쪽
    @JsonManagedReference(value = "member4")
    @OneToMany(mappedBy = "member" , cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<Review> review;

    // 막히면 안 되는 쪽
    @JsonManagedReference(value = "member6")
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<Qboard> qboard;

    // 막히면 안 되는 쪽
    @JsonManagedReference(value = "member7")
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<Token> token; 

    // 막히면 안 되는 쪽
    @JsonManagedReference(value = "member8")
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    // 정렬하기 no를 기준으로 내림차순
    @OrderBy(value = "no desc")
    private List<ReviewScore> reviewScore;

    @ToString.Exclude
    @OneToOne(mappedBy = "member")
    private Memberprofileimage memberprofileimage;

}
