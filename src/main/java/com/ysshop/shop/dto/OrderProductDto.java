package com.ysshop.shop.dto;
import lombok.Getter;
import lombok.Setter;
import com.ysshop.shop.entity.OrderProduct;

@Getter @Setter
public class OrderProductDto {

    public OrderProductDto(OrderProduct orderProduct) {
        this.name = orderProduct.getProduct().getName();
        this.count = orderProduct.getCount();
        this.orderPrice = orderProduct.getOrderPrice();
    }

    private String name;

    private int count;

    private int orderPrice;

}