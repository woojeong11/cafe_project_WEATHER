package com.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.admin.AdminFilter;

@Configuration
public class AdminFilterConfig {
    
    @Bean
    public FilterRegistrationBean<AdminFilter> AdminfilterRegistrationBean(AdminFilter adminFilter){

        FilterRegistrationBean<AdminFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(adminFilter);

        // 필터 적용할 url
        bean.addUrlPatterns(
            "/admin/*", 
            "/cafe/admin/*", 
            "/chat/admin/*",
            "/faq/adminselectlist.do"
        );

        return bean;
    }
}

