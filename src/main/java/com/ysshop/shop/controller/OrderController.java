package com.ysshop.shop.controller;

import com.ysshop.shop.dto.OrderDto;
import com.ysshop.shop.dto.PaymentInfoDto;
import com.ysshop.shop.entity.Order;
import com.ysshop.shop.repository.OrderRepository;
import com.ysshop.shop.service.OrderService;
import com.ysshop.shop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import com.ysshop.shop.dto.OrderHistDto;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity<?> order(@RequestBody @Valid OrderDto orderDto
            , Principal principal){
        String email = principal.getName();
        Long orderId;
        Order order;
        Map<String, Object> response = new HashMap<>();
        try {
            orderId = orderService.order(orderDto, email);
            order = orderRepository.findById(orderId).get();

            String uid = order.getUid();
            Long amount = order.getTotalPrice();
            response.put("uid", uid);
            response.put("amount", amount);

            paymentService.preValidatePayment(uid,amount);
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public ResponseEntity<Map<String, Object>> getOrderHistory(@RequestParam(value = "page", defaultValue = "0") int page, Principal principal) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("orders", ordersHistDtoList);
        response.put("page", pageable.getPageNumber());
        response.put("maxPage", 5);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
