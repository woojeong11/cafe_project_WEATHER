package com.example.dto;

import lombok.Data;

@Data
public class MemberDTO {
    // 일반회원가입 회원 필수정보, 선택정보 한 번에 담기위한 DTO

    // 필수 정보
    String userid;
    String userpw;
    String name;
    String nickname;
    String phone;

    // 선택 정보
    Long age;
    
    String email1;
    String email2;
    String gender = "null";

    String email;
    
}
