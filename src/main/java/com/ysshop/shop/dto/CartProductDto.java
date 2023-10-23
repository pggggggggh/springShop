package com.ysshop.shop.dto;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
@Getter @Setter
public class CartProductDto {
    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long productId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요")
    private int count;
}
