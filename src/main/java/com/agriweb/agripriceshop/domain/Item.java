package com.agriweb.agripriceshop.domain;

import com.agriweb.agripriceshop.dto.ItemDto;
import com.agriweb.agripriceshop.exception.NotEnoughStockException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private String desc;

    @ColumnDefault("0")
    private int price;

    @ColumnDefault("0")
    private int stockQuantity;

    private ItemCategory itemCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member register;

    private LocalDateTime regdate;

    private LocalDateTime update;

    @ColumnDefault("0")
    private int viewCount;

    @ColumnDefault("0")
    private int orderCount;


    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemPicture> pictures = new ArrayList<>();



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
        return item;
    }


    //== 비즈니스 로직==//
    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stockQuantity = restStock;
    }


}
