package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.Board;
import com.agriweb.agripriceshop.domain.Order;
import com.agriweb.agripriceshop.domain.OrderStatus;
import com.agriweb.agripriceshop.dto.OrderSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    // 주문 전부 가져오기
    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {

        String sql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                sql += " where";
                isFirstCondition = false;
            } else {
                sql += " and";
            }
            sql += " o.status = :status";
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getLoginId())) {
            if (isFirstCondition) {
                sql += " where";
                isFirstCondition = false;
            } else {
                sql += " and";
            }
            sql += " m.loginId like :login_id";

        }

        TypedQuery<Order> query = em.createQuery(sql, Order.class)
                .setMaxResults(1000); // 최대 1000건

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (orderSearch.getLoginId() != null) {
            query = query.setParameter("login_id", orderSearch.getLoginId());
        }

        return query.getResultList();

    }

    public List<Order> findOrdersByLoginId(String loginId) {
        String query = "select o from Order o join o.member m where m.loginId like :loginId";

        return em.createQuery(query, Order.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) {
        String query = "select o from Order o where o.status = :status";

        return em.createQuery(query, Order.class)
                .setParameter("status", orderStatus)
                .getResultList();
    }

    public List<Order> findOrdersByIdNStatus(String loginId, OrderStatus orderStatus) {
        String query = "select o from Order o join o.member m where o.status = :status and m.loginId like :loginId";

        return em.createQuery(query, Order.class)
                .setParameter("loginId", loginId)
                .setParameter("status", orderStatus)
                .getResultList();
    }




}
