package com.ysshop.shop.entity;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderProduct {

    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;     // 1.

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;   // 2.

    private int orderPrice;    // 주문 가격

    private int count;
}
