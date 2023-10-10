package com.ysshop.shop.controller;


import org.springframework.stereotype.Controller;;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String main() {
        return "main"; // 메인 홈페이지
    }
}
