package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.DeliveryStatus;
import com.agriweb.agripriceshop.domain.ItemCategory;
import com.agriweb.agripriceshop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@Data
public class OrderResponseDto {

    private Long orderId;
    private String loginId;
    private Long ItemId;
    private String itemName;
    private String itemDesc;
    private int itemPrice;
    private int count;
    private int totalPrice;
    private ItemCategory category;
    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime orderDate;
    private LocalDateTime orderCancelDate;
    private LocalDateTime orderCompleteDate;


}
