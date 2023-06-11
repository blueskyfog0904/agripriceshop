package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@Data
public class OrderDto {


    private Long orderId;
    private Long memberId;
    private List<OrderItem> orderItems;
    private Long itemId;
    private int count;
    private String deliveryName;
    private String deliveryAddr;
    private String deliveryTel;
    private LocalDateTime orderDate;
    private LocalDateTime orderCancelDate;
    private LocalDateTime orderCompleteDate;
    private String status;

}
