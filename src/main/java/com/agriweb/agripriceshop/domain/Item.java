package com.agriweb.agripriceshop.domain;

import com.agriweb.agripriceshop.dto.ItemDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member register;

    private LocalDateTime regdate;

    private LocalDateTime update;

    //==생성 메서드==//
    public static Item createItem(ItemDto dto, Member member, Category category) {
        Item item = new Item();
        item.setId(dto.getItemId());
        item.setName(dto.getName());
        item.setDesc(dto.getDesc());
        item.setPrice(dto.getPrice());
        item.setStockQuantity(dto.getStockQuantity());
        item.setCategory(category);
        item.setRegister(member);
        item.setRegdate(dto.getRegdate());
        item.setUpdate(dto.getUpdate());
        return item;
    }


}
