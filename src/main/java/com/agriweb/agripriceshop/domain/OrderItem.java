package com.agriweb.agripriceshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name="orderitem_id")
    private Long id;

    private Item item;

    private Order order;

    private int orderPrice;

    private int count;


}
