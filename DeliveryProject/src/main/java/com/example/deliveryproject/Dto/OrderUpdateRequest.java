package com.example.deliveryproject.Dto;

//기존 주문을 수정할 때 필요한 정보만 담아 전송하는 DTO
public record OrderUpdateRequest(Integer quantity, String status) {
}
