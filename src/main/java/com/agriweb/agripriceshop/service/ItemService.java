package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Item;
import com.agriweb.agripriceshop.domain.ItemCategory;
import com.agriweb.agripriceshop.domain.ItemPicture;
import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.ItemDto;
import com.agriweb.agripriceshop.repository.ItemPictureRepository;
import com.agriweb.agripriceshop.repository.ItemRepository;
import com.agriweb.agripriceshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.ImageProducer;
import java.io.File;
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
    private final ItemPictureRepository itemPictureRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final FileHandler fileHandler;

    // 아이템(상품) 등록
    public Item create(Item item, List<MultipartFile> files) throws Exception {

        itemRepository.save(item);

        // 파일을 저장하고 그 ItemPicture 에 대한 list를 가지고 있는다.
        List<ItemPicture> list = fileHandler.parseFileInfo(item.getId(), files);

        List<ItemPicture> pictureBeans = new ArrayList<>();
        for (ItemPicture itemPicture : list) {
            itemPictureRepository.save(itemPicture);
            pictureBeans.add(itemPicture);
        }
        item.setPictures(pictureBeans);

        return item;

    }

    // 아이템(상품) 수정
    public ItemDto update(Long itemId, ItemDto dto) {
        Item findItem = itemRepository.findOne(itemId);
        if (dto.getName() != null) {
            findItem.setName(dto.getName());
        }
        if (dto.getDesc() != null) {
            findItem.setDesc(dto.getDesc());
        }
        if (dto.getPrice() != 0) {
            findItem.setPrice(dto.getPrice());
        }
        if (dto.getStockQuantity() != 0) {
            findItem.setStockQuantity(dto.getStockQuantity());
        }
        if (dto.getCategory() != null) {
            findItem.setItemCategory(dto.getCategory());
        }
        if (dto.getUpdate() != null) {
            findItem.setUpdate(dto.getUpdate());
        }

        String loginId = findItem.getRegister().getLoginId();

        ItemDto updated = ItemDto.createItemDto(findItem, loginId);
        return updated;

    }

    // 아이템(상품) ID로 1개 조회
    public Item findOnebyId(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 아이템(상품) 전체 조회
    public Page<ItemDto> findItems(Pageable pageable) {

        // 조회: 아이템(상품) 목록
        Page<Item> itemPage = itemRepository.findAll(pageable);
        List<ItemDto> dtos = new ArrayList<>();


        for (Item item : itemPage) {
            Member member = memberRepository.findOne(item.getRegister().getId());
            String loginId = member.getLoginId();
            ItemDto dto = ItemDto.createItemDto(item, loginId);
            dtos.add(dto);
        }

        return new PageImpl<>(dtos, pageable, itemPage.getTotalElements());

    }

    // 아이템(상품) 카테고리로 조회
    public Page<ItemDto> findItemsByCategory(ItemCategory itemCategory, Pageable pageable) {
        // 조회 : 아이템(상품) 카테고리로 조회
        Page<Item> itemPage = itemRepository.findByCategory(itemCategory, pageable);

        // 변환 엔티티 -> Dto
        List<ItemDto> dtos = new ArrayList<>();

        for (Item item : itemPage) {
            Member member = memberRepository.findOne(item.getRegister().getId());
            String loginId = member.getLoginId();
            ItemDto dto = ItemDto.createItemDto(item, loginId);
            dtos.add(dto);
        }

        return new PageImpl<>(dtos, pageable, itemPage.getTotalElements());

//        for (int i = 0; i < items.size(); i++) {
//            Item item = items.get(i);
//            Member member = memberRepository.findOne(item.getRegister().getId());
//            String loginId = member.getLoginId();
//            ItemDto dto = ItemDto.createItemDto(item, loginId);
//            dtos.add(dto);
//
//        }
//        return dtos;
    }

    // 아이템(상품) 이름 검색으로 조회
    public Page<ItemDto> findItemsByName(String findName, Pageable pageable) {


        // 조회 : 아이템(상품) 이름 검색으로 조회
        Page<Item> itemPage = itemRepository.findByName(findName, pageable);

        // 변환 엔티티 -> Dto
        List<ItemDto> dtos = new ArrayList<>();

        for (Item item : itemPage) {
            Member member = memberRepository.findOne(item.getRegister().getId());
            String loginId = member.getLoginId();
            ItemDto dto = ItemDto.createItemDto(item, loginId);
            dtos.add(dto);
        }

        return new PageImpl<>(dtos, pageable, itemPage.getTotalElements());

    }


    // 아이템 삭제
    @Transactional
    public void delete(Long itemId) {
        Item target = itemRepository.findOne(itemId);

        // 삭제
        itemRepository.deleteOne(target);
    }

}
