package com.example.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.entity.Member;
import com.example.entity.Memberprofileimage;
import com.example.entity.Minfo;
import com.example.entity.Token;

@Service
public interface MemberService {
    
    // 회원 필수정보 등록
    public int Insertmember(Member member);

    // 회원 선택정보 등록
    public int Insertmemberinfo(Minfo member);

    // 회원 필수 정보 upsert
    public int upsertMember(Member member);

    // 회원 선택 정보 upsert
    public int upsertMinfo(Minfo minfo);

    // 회원 필수정보 selectone
    public Member memberSelectone( String userid );

    // 회원 선택정보 selectone
    public Minfo memberinfoselectone(String userid);

    // 토큰 정보 upsert
    public int upsertToken(Token token);

    // 프로필이미지 등록(일반회원만)
    public int insertprofileimage( Memberprofileimage image);

    // 프로필 이미지 멤버아이디 기반 조회(일반회원만)
    public Memberprofileimage selectoneprofileimage( Member member );
    
    // 프로필 이미지 수정(일반회원만)
    public int updateprofileimage( Memberprofileimage image );

    // 회원 필수정보 수정(일반회원만)
    public int updateMember( Member member );

    // 회원 선택입력정보 수정(일반회원만)
    public int updateMinfo( Minfo minfo );

    // 회원 비밀번호 수정
    public int updatePassword( Map<String, Object> map );

    // 회원 선택입력 정보 탈퇴처리
    public int deleteMinfo( String userid );

    // 회원 필수입력 정보 탈퇴처리
    public int updeleteMember(String userid);

    // 프로필 이미지 삭제(회원탈퇴시)
    public int deleteProfileImage( String userid );

    // 회원 id 유효성 검사(중복확인)
    public int idcheck(String userid);

    // 회원 아이디찾기
    public Member Email_Phone_selectMember(Map<String, Object> map);

    // 회원 프로필 url 수정
    public int updateprofileurl( Map<String, Object> map );
}