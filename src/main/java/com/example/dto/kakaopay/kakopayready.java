package com.example.dto.kakaopay;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class kakopayready {
    
    private String tid;
	private String next_redirect_pc_url;
	private String partner_order_id;
}
