package com.ysshop.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private String categoryTag;
    private String typeTag;
    private String color;
    private String size;
    private String composition;
    private String countryOfOrigin;
    private Boolean isPerPiece;
    private String thickness;
    private String transparency;
    private String elasticity;
    private String lining;
    private String laundryInfo;
    private String additionalInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

