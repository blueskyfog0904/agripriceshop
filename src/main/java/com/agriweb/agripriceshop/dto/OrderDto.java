package com.agriweb.agripriceshop.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@ToString
public class OrderDto {


    private Long orderId;
    private Long memberId;
    private List<OrderItemDto> orderItems;
    private DeliveryDto delivery;
    private LocalDateTime orderDate;
    private String status;

}
