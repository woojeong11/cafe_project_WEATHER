package com.example.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.entity.Member;
import com.example.entity.Minfo;
import com.example.entity.Token;

@Mapper
public interface MemberMapper {
    
    public int upsertMember(Member member); 

    public int upsertMinfo(Minfo minfo);

    public int upsertToken(Token token);

    // 일반 회원 선택정보등록
    public int insertMemberinfo( Minfo minfo);

    // 회원 필수정보 조회
    public Member selectoneMember( String userid );

    // 회원 선택정보 조회
    public Minfo selectoneMemberinfo( String userid );

    // 일반 회원 필수정보 수정
    public int UpdateMember(Member member);

    // 일반 회원 선택정보 수정
    public int UpdateMinfo(Minfo minfo);

    // 비밀번호 변경
    public int UpdatePassword( Map<String, Object> map );

    // 회원탈퇴 ( Minfo 는 삭제하고 Member는 id, name을 제외하고 null 처리한다. )
    // 회원 선택정보 삭제
    public int deleteMinfo( String userid );

    // 회원 필수입력 정보 탈퇴처리(수정)
    public int updeleteMember( String userid );

    // 회원 필수정보 조회
    public Member Email_Phone_selectMember( Map<String, Object> map );

    // 회원 프로필 url 수정
    public int updateprofileurl( Map<String, Object> map );

}