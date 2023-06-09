package com.agriweb.agripriceshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  // cascadetype.all 사용시 Order에다가 orderitems를 넣어두고 저장하면 Order에 저장됨
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column
    private LocalDateTime orderDate; // 주문 시간

    @Column
    private LocalDateTime orderCancelDate; // 주문 취소 시간

    @Column
    private LocalDateTime orderCompleteDate; // 주문 완료 시간


    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // 주문 상태 [ORDER, CANCEL, FINISH]

    //== 연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;

    }

    //== 비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        this.setOrderCancelDate(LocalDateTime.now());
        delivery.setStatus(DeliveryStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 주문 완료
     */
    public void finish() {
        if (this.getStatus() == OrderStatus.CANCEL) {
            throw new IllegalStateException("취소되었거나 완료된 주문은 취소가 불가능합니다.");
        }
        if (this.getStatus() == OrderStatus.FINISH) {
            throw new IllegalStateException("취소되었거나 완료된 주문은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.FINISH);
        this.setOrderCompleteDate(LocalDateTime.now());
        delivery.setStatus(DeliveryStatus.COMP);
    }





}
