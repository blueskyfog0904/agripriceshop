package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.Item;
import com.agriweb.agripriceshop.domain.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class ItemDto {

    private Long itemId;

    private String name;

    private String desc;

    private int price;

    private int stockQuantity;

    private ItemCategory category;

    private String loginId;

    private LocalDateTime regdate;

    private LocalDateTime update;

    private int viewCount;

    private int orderCount;

    //==생성 메서드==//
    public static ItemDto createItemDto(Item item, String loginId){
        ItemDto dto = new ItemDto();
        dto.setItemId(item.getId());
        dto.setName(item.getName());
        dto.setDesc(item.getDesc());
        dto.setPrice(item.getPrice());
        dto.setStockQuantity(item.getStockQuantity());
        dto.setCategory(item.getItemCategory());
        dto.setLoginId(loginId);
        dto.setRegdate(dto.getRegdate());
        dto.setUpdate(item.getUpdate());
        dto.setViewCount(item.getViewCount());
        dto.setOrderCount(item.getOrderCount());
        return dto;
    }



}
