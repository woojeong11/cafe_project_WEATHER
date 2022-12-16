package com.example.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Member;
import com.example.entity.Memberprofileimage;

@Repository
public interface ProfileImageRepository extends JpaRepository<Memberprofileimage, Long> {
    public Memberprofileimage findByMember(Member member);

    @Transactional
    public long deleteByMember(Member member);
}
