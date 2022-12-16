package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@DynamicInsert
@Table(name = "MINFOTBL")
public class Minfo {
    
    @Id
    @Column(name = "USERID")
    String userid;

    String age;

    @Column(length = 10)
    String gender;

    String url;

    @OneToOne
    @ToString.Exclude
    @MapsId //@MapsId 는 @id로 지정한 컬럼에 @OneToOne 이나 @ManyToOne 관계를 매핑시키는 역할
    @JoinColumn(name = "USERID")
    private Member member;
}
