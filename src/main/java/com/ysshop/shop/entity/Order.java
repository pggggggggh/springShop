package com.ysshop.shop.entity;

import com.ysshop.shop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 주문한 사용자 정보

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>(); // 주문 상품 목록

    private String deliveryAddress; // 배송 주소
    private String deliveryMethod; // 배송 방법: 일반택배, 당일특급 등
    private String paymentMethod; // 결제 방법: 토스 페이 등
    private LocalDateTime orderDate; // 주문 날짜
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태: 주문 확인, 배송 중, 배송 완료 등
    private String soldOutProcess; // 품절 상품 처리 방법: 전체 환불, 전체 미송, 상품별 선택 등
    
    // 결제 정보
    private double totalProductAmount; // 상품금액 합계
    private double purchaseFee; // 사입수수료
    private double deliveryFee; // 배송비
    private double totalAmount; // 총 결제금액
    
    // 주문/결제 동의 관련
    private boolean isAgreeWithTerms; // 주문상품 및 결제 대행 이용약관 동의

 public void addOrderProduct(OrderProduct orderProduct) {
  orderProducts.add(orderProduct);
  orderProduct.setOrder(this);
 }

 public static Order createOrder(User user, List<OrderProduct> orderProductList) {
  Order order = new Order();
  order.setUser(user);
  for(OrderProduct orderProduct : orderProductList) {
   order.addOrderProduct(orderProduct);
  }
  order.setOrderStatus(OrderStatus.ORDER);
  order.setOrderDate(LocalDateTime.now());
  return order;
 }
