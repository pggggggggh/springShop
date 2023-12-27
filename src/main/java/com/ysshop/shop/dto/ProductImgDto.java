package com.ysshop.shop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ysshop.shop.entity.Product;
import com.ysshop.shop.entity.ProductImg;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ProductImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private Boolean isRepImg; // 대표 이미지인가?

    private static ModelMapper modelMapper = new ModelMapper();

    public static ProductImgDto of(ProductImg productImg) {
        return modelMapper.map(productImg, ProductImgDto.class);
    }
}
