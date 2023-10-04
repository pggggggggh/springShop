package com.ysshop.shop.entity;

import jakarta.persistence.*;

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

    // Getters
    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategoryTag() {
        return categoryTag;
    }

    public String getTypeTag() {
        return typeTag;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getComposition() {
        return composition;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public Boolean getIsPerPiece() {
        return isPerPiece;
    }

    public String getThickness() {
        return thickness;
    }

    public String getTransparency() {
        return transparency;
    }

    public String getElasticity() {
        return elasticity;
    }

    public String getLining() {
        return lining;
    }

    public String getLaundryInfo() {
        return laundryInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategoryTag(String categoryTag) {
        this.categoryTag = categoryTag;
    }

    public void setTypeTag(String typeTag) {
        this.typeTag = typeTag;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public void setIsPerPiece(Boolean isPerPiece) {
        this.isPerPiece = isPerPiece;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }

    public void setElasticity(String elasticity) {
        this.elasticity = elasticity;
    }

    public void setLining(String lining) {
        this.lining = lining;
    }

    public void setLaundryInfo(String laundryInfo) {
        this.laundryInfo = laundryInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

