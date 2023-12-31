package com.ysshop.shop.repository;

import com.ysshop.shop.entity.Order;
import com.ysshop.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Long>  {
    @Query("select o from Order o " +
            "where o.user.email = :email " +
            "order by o.orderDate desc"
    )
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Order o " +
            "where o.user.email = :email"
    )
    Long countOrder(@Param("email") String email);

    Order findByUid(String uid);

//    Order findById(Long id);
}

