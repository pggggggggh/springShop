package com.ysshop.shop.dto;

public class PaymentInfoDto {
    public PaymentInfoDto(String uid, Long amount) {
        this.uid = uid;
        this.amount = amount;
    }

    String uid;
    Long amount;
}
