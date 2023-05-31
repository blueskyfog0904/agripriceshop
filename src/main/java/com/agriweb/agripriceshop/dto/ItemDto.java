package com.agriweb.agripriceshop.dto;

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

}
