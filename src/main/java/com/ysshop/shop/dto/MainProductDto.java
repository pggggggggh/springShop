package com.ysshop.shop.dto;
import com.querydsl.core.annotations.QueryProjection;
import com.ysshop.shop.constant.BodyType;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MainProductDto {
    private Long id;

    private String name;

    private BodyType bodyType;

    private Boolean isSoldOut;

    private String additionalDescription;

    private String imgUrl;

    private Integer price;

    @QueryProjection                        // 생성자에 @QueryProjection 어노테이션을 선언하여 Querydsl로 결과 조회 시 MainItemDto 객체로 바로 받아옴
    public MainProductDto(Long id, String name, BodyType bodyType, String additionalDescription, String imgUrl, Integer price) {
        this.id = id;
        this.name = name;
        this.bodyType = bodyType;
        this.additionalDescription = additionalDescription;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}

