package com.ysshop.shop.controller;

import com.ysshop.shop.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test")
public class TestController {
    @GetMapping(value="")
    public String test(@AuthenticationPrincipal User user) {
        return "hello " + user.getUsername();
    }
}
