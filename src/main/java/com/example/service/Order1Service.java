package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.Order1DTO;
import com.example.entity.PayResult;

@Service
public interface Order1Service {
   //일괄추가
   public int insertOrderBatch(List<Order1DTO> list);

   // 조회(페이용)
   public List<Order1DTO> selectOrderList( Map<String, Object> map ); 

   // 수정( 결제완료 시에 paycode 삽입 및 type 변경 )
   public int updateList( Map<String, Object> map );

   // 결제 취소나 결제 실패시 제거할 ordertbl(delete)
   public int deleteorder(String userid);

   // 배달 진행중인 건들 조회(결제는 완료된 상태)
   public List<PayResult> selectType2( Map<String, Object> map );

   // 배달 진행중인 건들 조회(결제는 완료된 상태)(결제마다의 구역 나눔용)
   public List<PayResult> selectType2ByPaycode( Map<String, Object> map );

   // 주문 완료건들조회(배달까지 완료된 상태)
   public List<PayResult> selectType0( Map<String, Object> map );

   // 주문 상세조회
   public PayResult selectoneOrder( Map<String, Object> map );

   // 관리자용 배달 진행중인 건들 조회(결제는 완료된 상태)
   public List<PayResult> selectType2foradmin( Map<String, Object> map );

   // 관리자용 주문 완료건들조회(배달까지 완료된 상태)
   public List<PayResult> selectType0foradmin( Map<String, Object> map );
   
   // 카운트 (갯수) 넣는 TYPE 번호에 따라 줌 ( CUSTOMER용 )
   public int countforcustomer( Map<String, Object> map );

   // 그냥 삭제
   public int cancelorder(String code);
}
