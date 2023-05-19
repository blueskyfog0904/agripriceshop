package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.Order;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class OrderItemDto {

    private Long orderItemId;
    private String itemId;
    private Order order;
    private int orderPrice;
    private int count;



}
