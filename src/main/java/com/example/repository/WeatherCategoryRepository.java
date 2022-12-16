package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.WeatherCategory;

@Repository
public interface WeatherCategoryRepository extends JpaRepository<WeatherCategory, String> {
    
    public WeatherCategory findByCategory(String category);
}
