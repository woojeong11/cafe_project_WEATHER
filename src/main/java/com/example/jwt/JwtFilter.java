package com.example.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
        )
            throws ServletException, IOException {
        
        try {
            System.out.println("--------------------------------JwtFilters-------------------------------");
            System.out.println(request.getRequestURI());
    
            // 1. session에 있는 token
            HttpSession session = request.getSession();

            String token = (String) session.getAttribute("TOKEN");
            
            // 토큰 키가 없다면
            if(token == null){
                throw new Exception(); // 강제로 오류발생
            }
            
            // 토큰 내용이 없다면
            if(token.length() <= 0){
                throw new Exception();
            }

            // 토큰 검증
            String username = jwtUtil.extractUsername(token);

            if(jwtUtil.validateToken(token, username) == false){
                throw new Exception();
            }

            // 권한에 맞지 않으면
            String role = jwtUtil.extractUserRole(token);

            System.out.println("-----------------------------filter--------------------------------------");
            System.out.println(username);
            System.out.println();
            System.out.println(role);
            System.out.println("-------------------------------filter------------------------------------");

            request.setAttribute("userid", username);
            request.setAttribute("role", role);

            // 아래가 수행되어야 restcontroller가 동작됨.
            filterChain.doFilter(request, response);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
