package com.ysshop.shop.controller;

import com.ysshop.shop.dto.OrderDto;
import com.ysshop.shop.entity.Order;
import com.ysshop.shop.repository.OrderRepository;
import com.ysshop.shop.service.OrderService;
import com.ysshop.shop.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    @PostMapping(value = "/complete")
    public @ResponseBody ResponseEntity paymentComplete(@RequestBody Map<String,String> paymentInfo
           , Principal principal){
        String email = principal.getName();
        String uid = paymentInfo.get("merchant_uid");
        String amount = paymentInfo.get("amount");
        Order order;
        try {
            order = orderRepository.findByUid(uid);

            if (Integer.parseInt(amount) != order.getTotalPrice()) {
                return new ResponseEntity<String>("조작된 결제입니다.", HttpStatus.BAD_REQUEST);
            }

            orderService.processOrder(order);
        } catch(Exception e) {
            try {
                paymentService.refund(uid, Long.parseLong(amount));
            } catch(Exception e2) {
                return new ResponseEntity<String>("결제 실패 : " + e.getMessage() + ", 환불 실패 : " + e2.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
