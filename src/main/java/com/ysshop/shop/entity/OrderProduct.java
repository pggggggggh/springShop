package com.ysshop.shop.entity;

import com.ysshop.shop.entity.Product;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderProduct extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;     // 1.

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;   // 2.

    private int orderPrice;    // 주문 가격

    private int count;
    public static OrderProduct createOrderProduct(Product product, int count) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setCount(count);
        orderProduct.setOrderPrice(product.getPrice());

        product.removeStock(count);
        return orderProduct;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }
}
