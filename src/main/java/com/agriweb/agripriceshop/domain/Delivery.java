package com.agriweb.agripriceshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    private String deliveryName;
    private String deliveryAddr;
    private String deliveryTel;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;


}
