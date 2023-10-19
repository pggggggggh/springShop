package com.ysshop.shop.entity;
import com.ysshop.shop.constant.BodyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import java.util.List;
import com.ysshop.shop.exception.OutOfStockException;
import java.time.LocalDateTime;
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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller; // 도매상 정보

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Column(nullable = false)
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

    @Lob
    private String additionalDescription;

    private Boolean isSoldOut; // 품절 여부

    @Column(nullable = false)
    private int stockQuantity; // 재고 수량

     // 상품 사진은 별도의 엔터티로 관리
    @OneToMany(mappedBy = "product")
    private List<ProductImg> productImages;


    public void removeStock(int stockNumber) {
        int restStock = this.stockQuantity - stockNumber;
        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다." +
                    "(현재 재고 수량: " + this.stockQuantity + ")");
        }
        this.stockQuantity = restStock;
    }
    public void updateItem(ProductFormDto productFormDto) {
        this.name = productFormDto.getName();
        this.price = productFormDto.getPrice();
        this.stockQuantity = productFormDto.getStockQuantity();
        this.additionalDescription = productFormDto.getAdditionalDescription();
        this.isSoldOut = productFormDto.getIsSoldOut();
    }
}

