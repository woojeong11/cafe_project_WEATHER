package com.example.admin;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.config.GlobalProperties;
import com.example.service.MemberService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminFilter extends OncePerRequestFilter {
    
    final MemberService mService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
        )
    {
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("LOGIN") == null){
                System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                response.sendRedirect(GlobalProperties.servepath + "/page403.do");
            }
            else{
                if(!session.getAttribute("LOGIN").equals("점주")){
                    System.out.println("ccccccccccccccccccccccccccccccccccccccccccc");
                    response.sendRedirect(GlobalProperties.servepath + "/page403.do");
                }
            }
    
            // 아래가 수행되어야 restcontroller가 동작됨.
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
