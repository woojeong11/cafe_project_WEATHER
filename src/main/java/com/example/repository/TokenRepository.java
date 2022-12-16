package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    
}
