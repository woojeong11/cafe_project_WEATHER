package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.WeatherCategory;
import com.example.repository.WeatherCategoryRepository;

@Service
public class WeatherCategoryServiceImpl implements WeatherCategoryService {

    @Autowired
    WeatherCategoryRepository wcRepository;

    // 조회
    @Override
    public WeatherCategory selectWeatherCategory(String code) {
        try {
            
            WeatherCategory weatherCategory = wcRepository.findById(code).orElse(null); 

            if(weatherCategory != null){
                return weatherCategory;
            }
            return null;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 수정
    @Override
    public int updateWeatherCategory(WeatherCategory weatherCategory) {
        try {
            
            WeatherCategory wcCategory = wcRepository.findById(weatherCategory.getCode()).orElse(null); 

            wcCategory.setImagedata(weatherCategory.getImagedata());
            wcCategory.setImagename(weatherCategory.getImagename());
            wcCategory.setImagesize(weatherCategory.getImagesize());
            wcCategory.setImagetype(weatherCategory.getImagetype());

            wcRepository.save(wcCategory);
            
            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 카테고리 기반 조회
    @Override
    public WeatherCategory selectBycategory(String category) {
        try {
            
            WeatherCategory weatherCategory = wcRepository.findByCategory(category); 

            if(weatherCategory != null){
                return weatherCategory;
            }
            return null;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
