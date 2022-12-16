package com.example.dto.kakaopay;

import java.util.List;

import com.example.dto.Order1DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class kakopayApprove {
    private String aid;
	private String tid;
	private String cid;
	private String sid;
	private String partner_order_id;
	private String partner_user_id;
	private String payment_method_type;
	private String item_name;
	private String item_code;
	private int quantity;
	private String created_at; 
	private String approved_at;
	private String payload;
	private kakaopayinfo amount;

}
