package com.example.entity;

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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.ToString;



@Data
@Entity
@Table(name = "ADDRESSTBL")
@SequenceGenerator(name = "ADDRESSSEQ", sequenceName = "SEQ_ADDRESS_NO", initialValue = 1001, allocationSize = 1)
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESSSEQ")
    Long no;

    @Column(length = 10)
    String post;

    @Column(length = 200)
    String address;

    String address2;

    String address3;

    // 대표 배송지 설정용
    Long rep = 0L;

    @ToString.Exclude
    @JsonBackReference(value = "member1")
    @ManyToOne
    @JoinColumn(name = "userid")
    private Member member;
}
