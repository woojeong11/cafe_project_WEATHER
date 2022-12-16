package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Token;
import com.example.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenRepository tRepository;

    // 등록
    @Override
    public int insertToken(Token token) {

        try {
            tRepository.save(token);
            return 1; 
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;            
        }
    }


    // 삭제
    @Override
    public int deleteToken(String token) {
        try {
            tRepository.deleteById(token);
            return 1; 
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;            
        }
    }
    
}
