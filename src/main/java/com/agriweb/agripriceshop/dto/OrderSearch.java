package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.OrderStatus;
import lombok.*;
import org.aspectj.weaver.ast.Or;


@AllArgsConstructor
@Data
@RequiredArgsConstructor
@Builder
public class OrderSearch {

    private String loginId; // 회원 ID
    private OrderStatus orderStatus; // 주문 상태 (ORDER, CANCEL, FINISH)

    // Setter 메서드 추가
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
