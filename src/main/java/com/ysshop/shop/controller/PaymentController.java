package com.ysshop.shop.controller;

import com.ysshop.shop.dto.OrderDto;
import com.ysshop.shop.entity.Order;
import com.ysshop.shop.repository.OrderRepository;
import com.ysshop.shop.service.OrderService;
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

    @PostMapping(value = "/complete")
    public @ResponseBody ResponseEntity<?> paymentComplete(@RequestBody Map<String,String> paymentInfo
           , Principal principal){
        String email = principal.getName();
        String uid = paymentInfo.get("merchant_uid");
        String amount = paymentInfo.get("amount");
        Order order;
        try {
            order = orderRepository.findByUid(uid);

            if (Integer.parseInt(amount) != order.getTotalPrice()) {
                throw new Exception();
            }
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("uid", order.getUid());
        response.put("amount", order.getTotalPrice());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
