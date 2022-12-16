package com.example.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    
    @Bean
    public JavaMailSender javaMailService(){

        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        
        javaMailSenderImpl.setHost("smtp.naver.com");   // 메인 도메인 주소
        javaMailSenderImpl.setUsername("ff5979@naver.com");       // 네이버 아이디
        javaMailSenderImpl.setPassword("roqkdnrhf1!");  // 네이버 비밀번호
        
        javaMailSenderImpl.setPort(465);                // 포트번호
        
        javaMailSenderImpl.setJavaMailProperties(getMailProperties()); // 메일 인증서버 정보 가져오기
        
        System.out.println("------------------------------java mail config-----------------------------------------------");
        System.out.println(javaMailSenderImpl);
        System.out.println("------------------------------java mail config-----------------------------------------------");
        
        return javaMailSenderImpl;
    }

    private Properties getMailProperties(){
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");          // 프로토콜 설정
        properties.setProperty("mail.smtp.auth", "true");                   // smtp 인증
        properties.setProperty("mail.smtp.starttls.enable", "true");        // smtp starttls 사용
        properties.setProperty("mail.debug","true");                        // 메일 디버그 사용
        properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com");    // ssl 인증 서버
        properties.setProperty("mail.smtp.ssl.enable", "true");             // ssl 사용
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 

        return properties;
    }
}
