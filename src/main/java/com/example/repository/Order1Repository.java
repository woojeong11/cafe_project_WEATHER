package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Order1;

@Repository
public interface Order1Repository extends JpaRepository<Order1, Long>{
}
