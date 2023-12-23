package com.ysshop.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainProductDto {
    private Long id;
    private String productName;
    private String imgUrl;
    private Integer price;

    @QueryProjection
    public MainProductDto(Long id, String productName, String imgUrl, Integer price) {
        this.id = id;
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
