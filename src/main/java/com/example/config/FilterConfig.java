package com.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.jwt.JwtFilter;

@Configuration
public class FilterConfig {
    
    @Bean
    public FilterRegistrationBean<JwtFilter> filterRegistrationBean(JwtFilter jwtfilter){

        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(jwtfilter);

        // 필터 적용할 url
        bean.addUrlPatterns(
            "/member/profileimageinsert.do",
            "/member/mypage.do",
            "/member/updateuserinfo.do",
            "/member/passwordupdate.do",
            "/member/delete.do",
            "/address/home.do",
            "/order1/home.do",
            "/order1/insert.do",
            "/review/selectlist.do",
            "/rest/review/insert.json",
            "/rest/review/like.json",
            "/review/insert.do",
            "/review/update.do",
            "/pay/kakao/completed.do",
            "/pay/kakao/paycancel.do",
            "/pay/kakao/payfail.do",
            "/pay/kakao/payresult.do",
            "/pay/naver/paying.do",
            "/member/orderinfo/ing.do",
            "/member/orderinfo/complete.do",
            "/member/orderinfo/myreview.do",
            "/qboard/insert.do",
            "/admin/qboard/repwrite.do",
            "/qboard/selectone.do",
            "/member/orderinfo/myaddress.do",
            "/address/repupdate.do",
            "/address/insert.do",
            "/chat/room",
            "/chat/admin/room"
        );

        return bean;
    }
}

