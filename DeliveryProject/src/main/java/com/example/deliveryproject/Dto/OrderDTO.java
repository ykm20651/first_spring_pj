package com.example.deliveryproject.Dto;

//주문 데이터 전송 시 OrderDTO 사용
//id: 주문 자체를 식별하는 고유한 ID.
public record OrderDTO(Long id, String menuName, Double menuPrice, Integer quantity, String status) {
}
