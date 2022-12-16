package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Token;

@Service
public interface TokenService {
    

    // 등록
    public int insertToken( Token token );

    // 토큰 삭제
    public int deleteToken( String token );
}
