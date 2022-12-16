package com.example.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.weatherListDTO;

@Service
public interface weatherService {
    
    public int insertbatch(weatherListDTO list);

    public int deleteWeatherData();

    public String selectWeatherListDTO(Map<String, Object> map);
}
