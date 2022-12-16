package com.example.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.weatherListDTO;

@Mapper
public interface weatherMapper {
    
    public int insertWeatherList(weatherListDTO list);

    public int deleteWeatherData();

    public String selectWeatherListDTO(Map<String, Object> map);
}
