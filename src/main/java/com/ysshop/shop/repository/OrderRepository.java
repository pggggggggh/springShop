package com.ysshop.shop.repository;

import com.ysshop.shop.entity.Order;
import com.ysshop.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>  {
}
