package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.Item;
import com.agriweb.agripriceshop.domain.ItemCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 아이템(상품) 저장
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }

    }


    // 아이템(상품) itemId로 1개 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // 아이템(상품) 전체 조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    // 아이템(상품) 카테고리로 조회
    public List<Item> findByCategory(ItemCategory itemCategory) {
        String query = "select i from Item i where i.itemCategory = :category";

        return em.createQuery(query, Item.class)
                .setParameter("category", itemCategory)
                .getResultList();
    }

    // 아이템(상품) 이름으로 조회
    public List<Item> findByName(String itemName) {
        String query = "select i from Item i where lower(i.name) LIKE CONCAT('%', LOWER(:itemName), '%')";

        return em.createQuery(query, Item.class)
                .setParameter("itemName", itemName)
                .getResultList();
    }

    // 아이템(상품) 삭제
    public void deleteOne(Item item) {
        em.remove(item);
    }



}
