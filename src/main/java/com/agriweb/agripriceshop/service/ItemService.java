package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Item;
import com.agriweb.agripriceshop.domain.ItemCategory;
import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.ItemDto;
import com.agriweb.agripriceshop.repository.ItemRepository;
import com.agriweb.agripriceshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    @Autowired
    private final ItemRepository itemRepository;

    @Autowired
    private final MemberRepository memberRepository;

    // 아이템(상품) 등록
    public Item create(Item item) {

        itemRepository.save(item);
        return item;

    }

    // 아이템(상품) ID로 1개 조회
    public Item findOnebyId(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 아이템(상품) 전체 조회
    public List<ItemDto> findItems() {

        // 조회: 아이템(상품) 목록
        List<Item> items =  itemRepository.findAll();

        // 변환 엔티티 -> Dto
        List<ItemDto> dtos = new ArrayList<ItemDto>();
        for (int i = 0; i  < items.size(); i++) {
            Item item = items.get(i);
            Member member = memberRepository.findOne(item.getRegister().getId());
            String loginId = member.getLoginId();
            ItemDto dto = ItemDto.createItemDto(item, loginId);
            dtos.add(dto);
        }
        return dtos;

    }

    // 아이템(상품) 카테고리로 조회
    public List<ItemDto> findItemsByCategory(ItemCategory itemCategory) {
        // 조회 : 아이템(상품) 카테고리로 조회
        List<Item> items = itemRepository.findByCategory(itemCategory);


        // 변환 엔티티 -> Dto
        List<ItemDto> dtos = new ArrayList<ItemDto>();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            Member member = memberRepository.findOne(item.getRegister().getId());
            String loginId = member.getLoginId();
            ItemDto dto = ItemDto.createItemDto(item, loginId);
            dtos.add(dto);

        }
        return dtos;
    }

    // 아이템(상품) 이름 검색으로 조회
    public List<ItemDto> findItemsByName(String findName) {
        // 조회 : 아이템(상품) 이름 검색으로 조회
        List<Item> items = itemRepository.findByName(findName);
회
        // 변환 엔티티 -> Dto
        List<ItemDto> dtos = new ArrayList<ItemDto>();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            Member member = memberRepository.findOne(item.getRegister().getId());
            String loginId = member.getLoginId();
            ItemDto dto = ItemDto.createItemDto(item, loginId);
            dtos.add(dto);

        }
        return dtos;

    }


    // 아이템 삭제
    @Transactional
    public void delete(Long itemId) {
        Item target = itemRepository.findOne(itemId);

        // 삭제
        itemRepository.deleteOne(target);
    }

}
