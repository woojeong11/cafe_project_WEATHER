package com.example.dto;

import java.util.List;

import com.example.entity.Weather;

import lombok.Data;

@Data
public class weatherListDTO {
    
    List<Weather> list;
}
