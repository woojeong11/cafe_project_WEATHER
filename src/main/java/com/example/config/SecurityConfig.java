package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.service.SecurityLogService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // thymeleaf를 이용한 로그인방식 때 사용
    @Autowired
    SecurityLogService  securityLogService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        // CustomDetailsService 파일과 연결
        http.userDetailsService(securityLogService);

        // 보안 취약 (비권장) H2-console 사용하기 위해
        // http.csrf().disable();

        // 일부의 주소만 csrf를 해제시킨다.
        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.csrf().ignoringAntMatchers("/chat/**");
        http.csrf().ignoringAntMatchers("/rest/recommend/insert.json");
        http.csrf().ignoringAntMatchers("/rest/recommend/insertall.json");
        http.csrf().ignoringAntMatchers("/rest/review/insert.json");
        http.csrf().ignoringAntMatchers("/rest/review/update.json");
        http.csrf().ignoringAntMatchers("/rest/review/like.json");
        http.csrf().ignoringAntMatchers("/rest/review/updateplusimage.json");
        http.csrf().ignoringAntMatchers("/rest/review/imagedelete.json");
        http.csrf().ignoringAntMatchers("/api/delete.json");
        http.csrf().ignoringAntMatchers("/rest/chat/insert.json");
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    // password를 hashpw로 변환 시켜 줌.
    // 객체생성
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
