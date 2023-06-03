package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String loginId; // 회원 ID
    private OrderStatus orderStatus; // 주문 상태 (ORDER, CANCEL, FINISH)
}
