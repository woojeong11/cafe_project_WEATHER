package com.example.teamproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@PropertySource("classpath:/global.properties")
@SpringBootApplication
@EnableScheduling 

@ComponentScan(basePackages = {
	"com.example.controller",
	"com.example.restcontroller",
	"com.example.service",
	"com.example.config",
	"com.example.handler",
	"com.example.jwt",
	"com.example.admin"
})

@EntityScan(basePackages = {"com.example.entity"})

@EnableJpaRepositories(basePackages = {"com.example.repository"})

@MapperScan(basePackages = {"com.example.mapper"})

public class TeamprojectApplication extends SpringBootServletInitializer{ 

	public static void main(String[] args) {
		SpringApplication.run(TeamprojectApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TeamprojectApplication.class);
	}

}
