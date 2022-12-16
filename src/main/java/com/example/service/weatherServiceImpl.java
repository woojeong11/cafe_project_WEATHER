package com.example.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.weatherListDTO;
import com.example.mapper.weatherMapper;

@Service 
public class weatherServiceImpl implements weatherService {

    @Autowired
    weatherMapper wMapper;

    @Override
    public int insertbatch(weatherListDTO list) {
        try {
            
            int ret = wMapper.insertWeatherList(list);

            if(ret > 0){
                return 1;
            }
            return 0;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteWeatherData() {
        try {
            
            int ret = wMapper.deleteWeatherData();

            if(ret > 0){
                return 1;
            }
            return 0;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 날씨 조회
    @Override
    public String selectWeatherListDTO(Map<String, Object> map) {
        try {
            
            String list = wMapper.selectWeatherListDTO(map);

            if(list != null){
                return list;
            }
            return null;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
