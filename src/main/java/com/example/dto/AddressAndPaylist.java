package com.example.dto;

import java.util.List;

import com.example.entity.PayResult;

import lombok.Data;

@Data
public class AddressAndPaylist {
    
    List<PayResult> pList;

    List<AddressinfoDTO> aList;
}
