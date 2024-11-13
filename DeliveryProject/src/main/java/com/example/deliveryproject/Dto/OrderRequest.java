package com.example.deliveryproject.Dto;

//새로운 주문을 추가할 때 필요한 정보만 담아 전송하는 DTO
public record OrderRequest(String menuName, Double menuPrice, Integer quantity) {
}
