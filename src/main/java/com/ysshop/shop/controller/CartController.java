package com.ysshop.shop.controller;
import com.ysshop.shop.dto.CartDetailDto;
import com.ysshop.shop.dto.CartOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import com.ysshop.shop.dto.CartProductDto;
import com.ysshop.shop.service.CartService;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<?> addCart(@RequestBody @Valid CartProductDto cartProductDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            String email = principal.getName();
            Long cartProductId = cartService.addCart(cartProductDto, email);
            return new ResponseEntity<>(cartProductId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartDetailDto>> getCartList(Principal principal) {
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        return ResponseEntity.ok(cartDetailList);
    }
    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> updateCartProduct(@PathVariable("cartProductId") Long cartProductId, int count, Principal principal){

        if(count <= 0){
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if(!cartService.validateCartProduct(cartProductId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartProductCount(cartProductId, count);
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }
    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> deleteCartProduct(@PathVariable("cartProductId") Long cartProductId, Principal principal) {

        if(!cartService.validateCartProduct(cartProductId, principal.getName())) {
            return new ResponseEntity<String> ("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartProduct(cartProductId);
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }
    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity<?> orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal){

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }

        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if(!cartService.validateCartProduct(cartOrder.getCartProductId(), principal.getName())){
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        Long orderId = cartService.orderCartProduct(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
