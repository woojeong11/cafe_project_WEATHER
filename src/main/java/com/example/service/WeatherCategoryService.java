package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.WeatherCategory;

@Service
public interface WeatherCategoryService {
    

    // 조회
    public WeatherCategory selectWeatherCategory(String code);

    // 수정
    public int updateWeatherCategory(WeatherCategory weatherCategory);

    // 카테고리 기반 조회
    public WeatherCategory selectBycategory(String category);
}
