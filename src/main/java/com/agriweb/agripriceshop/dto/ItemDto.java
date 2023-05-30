package com.agriweb.agripriceshop.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Data
@Builder
public class ItemDto {
    private Long itemId;

    private String name;

    private String desc;

    private int price;

    private int stockQuantity;

    private String category;

    private String loginId;

    private LocalDateTime regdate;

    private LocalDateTime update;

}
