package com.ysshop.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {

    private Long cartProductId;	// 장바구니 상품 아이디

    private String name;		// 상품명

    private int price;			// 상품 금액

    private int count;			// 수량

    private String imgUrl;		// 상품 이미지 경로

    public CartDetailDto(Long cartProductId, String name, int price, int count) {

        this.cartProductId = cartProductId;
        this.name = name;
        this.price = price;
        this.count = count;
    }
}