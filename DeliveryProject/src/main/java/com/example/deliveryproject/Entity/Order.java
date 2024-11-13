package com.example.deliveryproject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 주문자 자체를 구별하는 id ex) 주문번호 77번
    private String menuName;
    private Double menuPrice;
    private Integer quantity;
    private String status;

    public Order() {}

    public Order(String menuName, Double menuPrice, Integer quantity, String status) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.quantity = quantity;
        this.status = status;
    }

    // Getter와 Setter
    public Long getId() { return id; }
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public Double getMenuPrice() { return menuPrice; }
    public void setMenuPrice(Double menuPrice) { this.menuPrice = menuPrice; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
