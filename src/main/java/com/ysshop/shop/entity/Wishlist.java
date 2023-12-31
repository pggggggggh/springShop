package com.ysshop.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@Entity
@Table(name = "wishlists")
@NoArgsConstructor
public class Wishlist extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 찜한 사용자 정보

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // 찜한 상품 정보
    
    // BaseTimeEntity에 createdTime 있어서 생성 시각은 필요 X
}

