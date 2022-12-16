package com.example.dto.naverpay;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NaverPayReady {
    String code;
    String message;
    Object body;
}
