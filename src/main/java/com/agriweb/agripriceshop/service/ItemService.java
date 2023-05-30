package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Item;
import com.agriweb.agripriceshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ItemService {

    @Autowired
    private final ItemRepository itemRepository;

    // 아이템(상품) 등록
    @Transactional
    public Item saveItem(Item item) {
        itemRepository.save(item);
        Item created = itemRepository.findOne(item.getId());
        return created;
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
