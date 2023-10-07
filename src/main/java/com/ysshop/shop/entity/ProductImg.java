package com.ysshop.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_img")
@Getter
@Setter
public class ProductImg extends BaseEntity {
    @Id
    @Column(name = "product_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(length = 1000)
    private byte[] imgData;

    private Boolean isRepImg; // 대표 이미지인가?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
