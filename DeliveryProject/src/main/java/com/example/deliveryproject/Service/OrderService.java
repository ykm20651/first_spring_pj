package com.example.deliveryproject.Service;

import com.example.deliveryproject.Dto.OrderDTO;
import com.example.deliveryproject.Dto.OrderRequest;
import com.example.deliveryproject.Dto.OrderUpdateRequest;
import com.example.deliveryproject.Entity.Order;
import com.example.deliveryproject.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 모든 주문 조회
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll(); // 모든 주문을 가져옴
        List<OrderDTO> orderDTOList = new ArrayList<>(); // OrderDTO 객체를 담을 리스트 생성 -> 결국 주문 데이터의 내용을 모두 담고 있는 DTO는 OrderDTO이기에 여기에 데이터를 담을거임.

        for (Order order : orders) {
            // 각 Order 객체를 OrderDTO로 변환하여 리스트에 추가
            OrderDTO orderDTO = new OrderDTO(
                    order.getId(),
                    order.getMenuName(),
                    order.getMenuPrice(),
                    order.getQuantity(),
                    order.getStatus()
            );
            orderDTOList.add(orderDTO); // 변환된 OrderDTO를 orderDTOList 리스트에 추가
        }

        return orderDTOList; // OrderDTO 리스트 반환
    }

    // 주문 추가
    public OrderDTO addOrder(OrderRequest request) {
        // OrderRequest 데이터를 기반으로 새로운 Order 엔티티 생성
        Order addingOrder = new Order(
                request.menuName(),
                request.menuPrice(),
                request.quantity(),
                "Ordered"
        );

        // 생성된 Order 엔티티를 데이터베이스에 저장
        Order savedOrder = orderRepository.save(addingOrder);

        // 저장된 Order 엔티티를 OrderDTO로 변환하여 반환
        OrderDTO savingDTO = new OrderDTO(
                savedOrder.getId(),
                savedOrder.getMenuName(),
                savedOrder.getMenuPrice(),
                savedOrder.getQuantity(),
                savedOrder.getStatus()
        );

        return savingDTO;
    }

    // 주문 수정
    public OrderDTO updateOrder(Long id, OrderUpdateRequest updateRequest) {
        // 특정 ID의 Order 엔티티를 조회하고, 없으면 예외 발생 (람다식 없이 일반 함수로 표현)
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order updatingOrder = optionalOrder.orElseThrow(new java.util.function.Supplier<RuntimeException>() {
            @Override
            public RuntimeException get() {
                return new RuntimeException("Order not found");
            }
        });

        // Order 객체의 수량과 상태를 업데이트
        updatingOrder.setQuantity(updateRequest.quantity());
        updatingOrder.setStatus(updateRequest.status());

        // 업데이트된 Order 객체를 데이터베이스에 저장
        Order updatedOrder = orderRepository.save(updatingOrder);

        // 저장된 Order 엔티티를 OrderDTO로 변환하여 반환
        OrderDTO orderDTO = new OrderDTO(
                updatedOrder.getId(),
                updatedOrder.getMenuName(),
                updatedOrder.getMenuPrice(),
                updatedOrder.getQuantity(),
                updatedOrder.getStatus()
        );

        return orderDTO;
    }

    // 주문 삭제
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id); // 특정 ID의 주문을 데이터베이스에서 삭제
    }
}
