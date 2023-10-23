package com.ysshop.shop.service;
import com.ysshop.shop.dto.CartDetailDto;
import com.ysshop.shop.dto.CartOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import com.ysshop.shop.dto.CartProductDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


import com.ysshop.shop.dto.OrderDto;
import com.ysshop.shop.entity.*;
import com.ysshop.shop.repository.*;



@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderService orderService;

    public Long addCart(CartProductDto cartProductDto, String email) {

        Product product = productRepository.findById(cartProductDto.getProductId())
                .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email);

        Cart cart = cartRepository.findByUserId(user.getId());

        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        CartProduct savedCartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (savedCartProduct != null) {
            savedCartProduct.addCount(cartProductDto.getCount());
            return savedCartProduct.getId();
        } else {
            CartProduct cartProduct = CartProduct.createCartProduct(cart, product, cartProductDto.getCount());
            cartProductRepository.save(cartProduct);
            return cartProduct.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        User user = userRepository.findByEmail(email);
        Cart cart = cartRepository.findByUserId(user.getId()); // 현재 로그인한 회원 장바구니 엔티티 조회
        if(cart == null){  // 장바구니에 상품을 담지 않았을 경우 빈 리스트 반환
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartProductRepository.findCartDetailDtoList(cart.getId());
        // 장바구니에 담겨있는 상품 정보를 조회
        return cartDetailDtoList;
    }
    @Transactional(readOnly = true)
    public boolean validateCartProduct(Long cartProductId, String email){
        User curUser = userRepository.findByEmail(email);
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);
        User savedUser = cartProduct.getCart().getUser();

        if(!StringUtils.equals(curUser.getEmail(), savedUser.getEmail())){
            return false;
        }

        return true;
    }

    public void updateCartProductCount(Long cartProductId, int count){
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);

        cartProduct.updateCount(count);
    }
    public void deleteCartProduct(Long cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);
        cartProductRepository.delete(cartProduct);
    }
    public Long orderCartProduct(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            // --- 1.
            CartProduct cartProduct = cartProductRepository
                    .findById(cartOrderDto.getCartProductId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setProductId(cartProduct.getProduct().getId());
            orderDto.setCount(cartProduct.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, email);
        // --- 2.
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            // --- 3.
            CartProduct cartProduct = cartProductRepository
                    .findById(cartOrderDto.getCartProductId())
                    .orElseThrow(EntityNotFoundException::new);
            cartProductRepository.delete(cartProduct);
        }

        return orderId;
    }
}
