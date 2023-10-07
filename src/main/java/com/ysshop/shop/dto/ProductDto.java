package com.ysshop.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class ProductDto {
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
}
