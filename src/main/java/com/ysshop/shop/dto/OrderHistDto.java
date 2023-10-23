package com.ysshop.shop.dto;
import com.ysshop.shop.constant.OrderStatus;
import com.ysshop.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class OrderHistDto {

    public OrderHistDto(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    private Long id;

    private String orderDate;

    private OrderStatus orderStatus;

    private List<OrderProductDto> orderProductDtoList = new ArrayList<>();

    public void addOrderItemDto(OrderProductDto orderProductDto) {
        orderProductDtoList.add(orderProductDto);
    }
}
