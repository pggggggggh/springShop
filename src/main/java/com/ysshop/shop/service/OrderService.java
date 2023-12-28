package com.ysshop.shop.service;


import com.ysshop.shop.constant.OrderStatus;
import com.ysshop.shop.dto.OrderDto;
import com.ysshop.shop.dto.OrderProductDto;
import com.ysshop.shop.entity.*;
import com.ysshop.shop.exception.OutOfStockException;
import com.ysshop.shop.repository.ProductImgRepository;
import com.ysshop.shop.repository.ProductRepository;
import com.ysshop.shop.repository.UserRepository;
import com.ysshop.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ysshop.shop.dto.OrderHistDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductImgRepository productImgRepository;

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

    @Transactional // 재고 감소시키다가 오류 나면 롤백해야 함
    public void processOrder(Order order) {
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            orderProduct.getProduct().removeStock(orderProduct.getCount());
        }
        order.setOrderStatus(OrderStatus.PAID);
    }

    @Transactional
    public void cancelOrder(Order order) { // 위변조, 재고 없음 등의 사유로 환불

    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderProduct> orderProducts = order.getOrderProducts();
            for (OrderProduct orderProduct : orderProducts) {
                ProductImg productImg = productImgRepository.findByProductIdAndIsRepImg
                        (orderProduct.getProduct().getId(), Boolean.TRUE);
                OrderProductDto orderProductDto =
                        new OrderProductDto(orderProduct);
                orderHistDto.addOrderItemDto(orderProductDto);
            }

            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }
    public Long orders(List<OrderDto> orderDtoList, String email){

        User user = userRepository.findByEmail(email);
        List<OrderProduct> orderProductList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Product product = productRepository.findById(orderDto.getProductId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getCount());
            orderProductList.add(orderProduct);
        }

        Order order = Order.createOrder(user, orderProductList);
        orderRepository.save(order);

        return order.getId();
    }
}
