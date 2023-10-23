package com.ysshop.shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ysshop.shop.entity.Cart;
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
