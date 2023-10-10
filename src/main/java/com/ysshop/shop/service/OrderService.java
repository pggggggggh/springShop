package com.ysshop.shop.service;


import com.ysshop.shop.dto.OrderDto;
import com.ysshop.shop.entity.*;
import com.ysshop.shop.repository.ProductRepository;
import com.ysshop.shop.repository.UserRepository
import com.ysshop.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {
        Product product = productRepository.findById(orderDto.getProductId())
                .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email);

        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getCount());
        orderProductList.add(orderProduct);

        Order order = Order.createOrder(user, orderProductList);
        orderRepository.save(order);

        return order.getId();
    }
}