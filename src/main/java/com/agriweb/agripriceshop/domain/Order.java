package com.agriweb.agripriceshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    private Member member;

    private List<OrderItem> orderItems;

    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatus status;



}
