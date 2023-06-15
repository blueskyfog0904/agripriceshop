package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.ItemPicture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemPictureRepository {

    private final EntityManager em;

    public void save(ItemPicture itemPicture) {
        em.persist(itemPicture);
    }

    public List<ItemPicture> findAll(Long itemId) {
        TypedQuery<ItemPicture> query = em.createQuery("select p from ItemPicture p where p.itemId = :itemId", ItemPicture.class);
        query.setParameter("itemId", itemId);

        return query.getResultList();
    }

}
