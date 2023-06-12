package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.Item;
import com.agriweb.agripriceshop.domain.ItemCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

//    // 아이템(상품) 전체 조회
//    public List<Item> findAll() {
//        return em.createQuery("select i from Item i", Item.class)
//                .getResultList();
//    }

    // 아이템(상품) 전체 조회(페이징)
    public Page<Item> findAll(Pageable pageable) {
        TypedQuery<Item> query = em.createQuery("select i from Item i order by i.id desc", Item.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Item> items = query.getResultList();
        long total = getTotalCount();

        return new PageImpl<>(items, pageable, total);
    }

    private long getTotalCount() {
        TypedQuery<Long> countQuery = em.createQuery("select count(i) from Item i", Long.class);
        return countQuery.getSingleResult();
    }
    // 아이템(상품) 카테고리로 조회(페이징)
    public Page<Item> findByCategory(ItemCategory itemCategory, Pageable pageable) {
        TypedQuery<Item> query = em.createQuery("select i from Item i where i.itemCategory = :category order by i.id desc", Item.class);
        query.setParameter("category", itemCategory);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Item> items = query.getResultList();
        long total = getTotalCountByCategory(itemCategory);

        return new PageImpl<>(items, pageable, total);

    }

    private long getTotalCountByCategory(ItemCategory itemCategory) {
        TypedQuery<Long> countQuery = em.createQuery("select count(i) from Item i where i.itemCategory = : category", Long.class);
        countQuery.setParameter("category", itemCategory);
        return countQuery.getSingleResult();
    }


//    // 아이템(상품) 카테고리로 조회
//    public List<Item> findByCategory(ItemCategory itemCategory) {
//        String query = "select i from Item i where i.itemCategory = :category";
//
//        return em.createQuery(query, Item.class)
//                .setParameter("category", itemCategory)
//                .getResultList();
//    }

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
