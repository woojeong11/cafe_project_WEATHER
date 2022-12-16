package com.example.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.handler.CustomHandlerInterceptor;


// application.properties에 등록
// 서버를 구동할때 미리 자동으로 수행됨
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    // 자동으로 객체생성
    @Autowired
    CustomHandlerInterceptor handlerInterceptor;

    // 인터셉터 클래스를 등록하고 필터할 url주소를 설정 제거, 추가
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( handlerInterceptor )
            .addPathPatterns("/**")
            .excludePathPatterns( 
                "/recommend/imageselect**",
                "/api/**",
                "/member/**/login.do",
                "/img/**",
                "/productimage/**",
                "/js/**",
                "/css/**",
                "/lib/**",
                "/kakao/**",
                "/naver/**",
                "/member/upsert.do",
                "/error/**",
                "/member/img/**",
                "/member/updateuserinfo.do",
                "/member/imageselect**",
                "/member/passwordupdate.do",
                "/member/findid.do",
                "/admin/recommend/deletemany.do",
                "/admin/recommend/imageselect**",
                "/cart/insert.do",
                "/review/image",
                "/review/guestselectlist.do",
                "/rest/cart/count.json",
                "/weather/imageselect",
                "/undefined"
            );
    }
    
}