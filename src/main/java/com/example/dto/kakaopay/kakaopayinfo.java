package com.example.dto.kakaopay;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class kakaopayinfo {
    private int total;
	private int tax_free;
	private int vat;
	private int point;
	private int discount;
}
