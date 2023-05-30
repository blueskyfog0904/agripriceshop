package com.agriweb.agripriceshop.dto;

import com.agriweb.agripriceshop.domain.ItemCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    private String loginId;

    private LocalDateTime regdate;

    private LocalDateTime update;

}
