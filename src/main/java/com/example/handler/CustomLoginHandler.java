package com.example.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response,
        Authentication authentication
        ) throws IOException, ServletException {
        
        // Details Service에서 리턴했던 타입으로 꺼내기
        // 아이디, 암호, 권한들
        User user = (User) authentication.getPrincipal(); 
        
        // 권한에 따라 로그인 시 보낼 주소
        String role = user.getAuthorities().toArray()[0].toString();

        if(role.equals("ADMIN")){
            response.sendRedirect(
                request.getContextPath()+"/admin/home.do");
        }
        else if(role.equals("CUSTOMER")) {
            response.sendRedirect(
                request.getContextPath()+"/customer/home.do");
        }
        else {
            response.sendRedirect(
                request.getContextPath()+"/");
        }
        
    }
    
}
