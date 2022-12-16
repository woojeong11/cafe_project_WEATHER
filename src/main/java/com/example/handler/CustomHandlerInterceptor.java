package com.example.handler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class CustomHandlerInterceptor implements
            HandlerInterceptor{
    final String format = "INTERCEPTOR => {}";
        
    // 컨트롤러 진입후 view 표시전에 수행
    @Override
    public void postHandle(
            HttpServletRequest request, 
            HttpServletResponse response, 
            Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        log.info(format, "postHandle");
    }

    // 컨트롤러 진입전에 수행
    @Override
    public boolean preHandle(
            HttpServletRequest request, 
            HttpServletResponse response, 
            Object handler) throws Exception {

        log.info(format, "preHandle");    
        HttpSession httpSession = request.getSession();                
        
        if( request.getQueryString() == null) {
            httpSession.setAttribute("CURRENT_URL", 
                request.getServletPath());
        }
        else {
            httpSession.setAttribute("CURRENT_URL", 
                request.getServletPath()
                + "?" +  request.getQueryString());
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
