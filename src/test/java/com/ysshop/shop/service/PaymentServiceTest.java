package com.ysshop.shop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class PaymentServiceTest {
    @Autowired
    PaymentService paymentService;

    @Test
    @DisplayName("get token test")
    public void getIamportAccessTokenTest() {
        System.out.println(paymentService.getIamportAccessToken());
    }
}
