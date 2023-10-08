package com.ysshop.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
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
    private List<OrderItem> orderItems; // 주문 상품 목록

    private String deliveryAddress; // 배송 주소
    private String deliveryMethod; // 배송 방법: 일반택배, 당일특급 등
    private String paymentMethod; // 결제 방법: 토스 페이 등
    private Date orderDate; // 주문 날짜
    private String orderStatus; // 주문 상태: 주문 확인, 배송 중, 배송 완료 등
    private String soldOutProcess; // 품절 상품 처리 방법: 전체 환불, 전체 미송, 상품별 선택 등
    
    // 결제 정보
    private double totalProductAmount; // 상품금액 합계
    private double purchaseFee; // 사입수수료
    private double deliveryFee; // 배송비
    private double totalAmount; // 총 결제금액
    
    // 주문/결제 동의 관련
    private boolean isAgreeWithTerms; // 주문상품 및 결제 대행 이용약관 동의
}

