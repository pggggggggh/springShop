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
@Table(name = "products")
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller; // 도매상 정보

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
    private String additionalDescription;
    private boolean isSoldOut; // 품절 여부
    private int stockQuantity; // 재고 수량

     // 상품 사진은 별도의 엔터티로 관리
    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;


    // 생성한 사람은 createdBy로 조회
}

