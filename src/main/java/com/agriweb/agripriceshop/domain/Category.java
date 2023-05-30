package com.agriweb.agripriceshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Item> items;


}
