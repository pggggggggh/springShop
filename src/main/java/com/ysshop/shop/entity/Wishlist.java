package com.ysshop.shop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlists")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wishlistId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Getters
    public Integer getWishlistId() {
        return wishlistId;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    // Setters
    public void setWishlistId(Integer wishlistId) {
        this.wishlistId = wishlistId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

