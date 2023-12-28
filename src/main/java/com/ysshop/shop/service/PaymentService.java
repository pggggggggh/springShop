package com.ysshop.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final WebClient webClient;
    @Value("${portone.apikey}")
    String imp_key;
    @Value("${portone.apisecret}")
    String imp_secret;

    @Bean
    public static WebClient webClient() {
        return WebClient.builder().build();
    }

    public void preValidatePayment(String uid, Long amount) {
        String url = "https://api.iamport.kr/payments/prepare";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("merchant_uid", uid);
        formData.add("amount", amount.toString());

        String token = getIamportAccessToken();

        String response = webClient.post()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getIamportAccessToken() {
        String url = "api.iamport.kr/users/getToken";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("imp_key", imp_key);
        formData.add("imp_secret", imp_secret);

        Map<String,Map<String,String>> response = webClient.post()
                .uri(url)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(Map.class)    // 응답이 한번 돌아오고, 응답의 body를 String으로 해석
                .block();

        String token = response.get("response").get("access_token");

        return token;
    }

    public String refund(String uid, Long amount) {
        String url = "https://api.iamport.kr/payments/cancel";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("merchant_uid", uid);
        formData.add("cancel_request_amount", amount.toString());

        String token = getIamportAccessToken();

        String response = webClient.post()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();
//        System.out.println(response);

        return response;
    }
}
