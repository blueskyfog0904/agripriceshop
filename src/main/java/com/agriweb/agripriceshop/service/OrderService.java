package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.*;
import com.agriweb.agripriceshop.dto.OrderDto;
import com.agriweb.agripriceshop.dto.OrderResponseDto;
import com.agriweb.agripriceshop.dto.OrderSearch;
import com.agriweb.agripriceshop.repository.ItemRepository;
import com.agriweb.agripriceshop.repository.MemberRepository;
import com.agriweb.agripriceshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */

    @Transactional
    public Long order(Long memberId, OrderDto orderDto) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(orderDto.getItemId());

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setDeliveryName(orderDto.getDeliveryName());
        delivery.setDeliveryAddr(orderDto.getDeliveryAddr());
        delivery.setDeliveryTel(orderDto.getDeliveryTel());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderDto.getCount());

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        delivery.setOrder(order);
        delivery.setStatus(DeliveryStatus.READY);
        return order.getId();

    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    public Long finishOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 완료
        order.finish();
        return order.getId();
    }

    // 주문 검색(전체)
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    // 주문 검색(상태 또는 구매자이름)
    public Page<OrderResponseDto> findOrders(OrderSearch orderSearch, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByString(orderSearch, pageable);
        List<OrderResponseDto> ordersDtos = new ArrayList<OrderResponseDto>();
        for (Order order : orders) {
            OrderResponseDto orderDto = new OrderResponseDto();
            //OrderResponseDto set
            orderDto.setOrderId(order.getId());
            orderDto.setLoginId(order.getMember().getLoginId());
            orderDto.setItemId(order.getOrderItems().get(0).getItem().getId());
            orderDto.setItemName(order.getOrderItems().get(0).getItem().getName());
            orderDto.setItemDesc(order.getOrderItems().get(0).getItem().getDesc());
            orderDto.setItemPrice(order.getOrderItems().get(0).getItem().getPrice());
            orderDto.setCount(order.getOrderItems().get(0).getCount());
//            orderDto.setTotalPrice((order.getOrderItems().get(0).getItem().getPrice()*
//                    order.getOrderItems().get(0).getItem().getOrderCount()));
            orderDto.setTotalPrice(order.getOrderItems().get(0).getTotalPrice());
            orderDto.setCategory(order.getOrderItems().get(0).getItem().getItemCategory());
            orderDto.setOrderStatus(order.getOrderItems().get(0).getOrder().getStatus());
            orderDto.setOrderDate(order.getOrderItems().get(0).getOrder().getOrderDate());
            orderDto.setOrderCancelDate(order.getOrderCancelDate());
            orderDto.setOrderCompleteDate(order.getOrderCompleteDate());
            orderDto.setDeliveryStatus(order.getDelivery().getStatus());

            ordersDtos.add(orderDto);
        }
        return new PageImpl<>(ordersDtos, pageable, orders.getTotalElements());

//        for (int i =0; i < orders.size(); i++) {
//            OrderResponseDto orderDto = new OrderResponseDto();
//            Order order = orders.get(i);
//
//            //OrderResponseDto set
//            orderDto.setOrderId(order.getId());
//            orderDto.setLoginId(order.getMember().getLoginId());
//            orderDto.setItemId(order.getOrderItems().get(0).getItem().getId());
//            orderDto.setItemName(order.getOrderItems().get(0).getItem().getName());
//            orderDto.setItemDesc(order.getOrderItems().get(0).getItem().getDesc());
//            orderDto.setItemPrice(order.getOrderItems().get(0).getItem().getPrice());
//            orderDto.setCount(order.getOrderItems().get(0).getCount());
////            orderDto.setTotalPrice((order.getOrderItems().get(0).getItem().getPrice()*
////                    order.getOrderItems().get(0).getItem().getOrderCount()));
//            orderDto.setTotalPrice(order.getOrderItems().get(0).getTotalPrice());
//            orderDto.setCategory(order.getOrderItems().get(0).getItem().getItemCategory());
//            orderDto.setOrderStatus(order.getOrderItems().get(0).getOrder().getStatus());
//            orderDto.setOrderDate(order.getOrderItems().get(0).getOrder().getOrderDate());
//            orderDto.setOrderCancelDate(order.getOrderCancelDate());
//            orderDto.setOrderCompleteDate(order.getOrderCompleteDate());
//            orderDto.setDeliveryStatus(order.getDelivery().getStatus());
//
//            ordersDtos.add(orderDto);
//        }

    }

    // 주문 검색(회원 ID로)
    public List<Order> findOrdersByLoginId(String loginId) {
        return orderRepository.findOrdersByLoginId(loginId);
    }

    // 주문 검색(OrderStatus로)
    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findOrdersByOrderStatus(orderStatus);
    }

    // 주문 검색(회원 ID & Orderstatus)
    public List<Order> findOrdersByIdNStatus(String loginId, OrderStatus orderStatus) {
        return orderRepository.findOrdersByIdNStatus(loginId, orderStatus);
    }



    // 주문 Id로 1개 검색
    public Order findOne(Long orderId) {
        return orderRepository.findOne(orderId);
    }


}
