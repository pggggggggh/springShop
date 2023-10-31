package com.ysshop.shop.dto;
import com.ysshop.shop.config.EnumValue;
import lombok.Getter;
import lombok.Setter;
import com.ysshop.shop.constant.BodyType;
import com.ysshop.shop.entity.Product;
import com.ysshop.shop.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductFormDto {
    private Long id;

//    @NotBlank(message = "도매상 정보는 필수 입력 값입니다.")
//    private User seller; // 도매상 정보

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price;

    @NotBlank(message = "체형은 필수 입력 값입니다.")
    @EnumValue(enumClass = BodyType.class)
    private String bodyType;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String name;

    private String type;
    private String style;
    private String color;
    private String size;
    private String composition;
    private String countryOfManufacture;
    private boolean isPerPiece;
    private String registrationInfo;
    private String thickness;
    private String transparency;
    private String elasticity;
    private String lining;
    private String laundryInfo;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    private String additionalDescription;

    private Boolean isSoldOut; // 품절 여부

    private int stockQuantity; // 재고 수량

    private List<ProductImgDto> productImgDtoList = new ArrayList<>(); // 1.

    private List<Long> productImgIds = new ArrayList<>();  		 // 2.

    private static ModelMapper modelMapper = new ModelMapper();

    public Product createProduct() {

        return modelMapper.map(this, Product.class);				 // 3.
    }
    public static ProductFormDto of(Product product) {
        return modelMapper.map(product, ProductFormDto.class);         // 4.
    }
}
