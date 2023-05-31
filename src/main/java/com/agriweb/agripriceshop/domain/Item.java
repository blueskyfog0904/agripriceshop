package com.agriweb.agripriceshop.domain;

import com.agriweb.agripriceshop.dto.ItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private String desc;

    private int price;

    private int stockQuantity;

    private ItemCategory itemCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member register;

    private LocalDateTime regdate;

    private LocalDateTime update;

    private int viewCount;

    private int orderCount;

    //== 생성 메서드==/
    public static Item createItem(ItemDto dto, Member loginMember) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setDesc(dto.getDesc());
        item.setPrice(dto.getPrice());
        item.setStockQuantity(dto.getStockQuantity());
        item.setItemCategory(dto.getCategory());
        item.setRegister(loginMember);
        item.setRegdate(dto.getRegdate());
        item.setViewCount(0);
        item.setOrderCount(0);
        return item;
    }


}
