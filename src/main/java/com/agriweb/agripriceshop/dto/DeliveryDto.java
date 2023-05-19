package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.DeliveryStatus;
import com.agriweb.agripriceshop.domain.Order;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class DeliveryDto {

    private Long deliveryId;
    private String city;
    private String street;
    private String zipcode;
    private Order order;
    private DeliveryStatus status;

}
