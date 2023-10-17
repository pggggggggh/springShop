package com.ysshop.shop.controller;

import com.ysshop.shop.dto.NewUserDto;
import com.ysshop.shop.entity.User;
import com.ysshop.shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUserDto newUserDto) {
        User user = User.createUser(newUserDto, passwordEncoder);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/new")
    public ResponseEntity<?> newMember(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult) {

        // @RequestBody 어노테이션은 HTTP 요청 본문을 자바 객체로 변환합니다.

        if(bindingResult.hasErrors()) {
            // 에러가 발생한 경우, 에러 메시지와 함께 BAD_REQUEST 상태를 반환
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            User user = User.createUser(newUserDto, passwordEncoder);
            userService.saveUser(user);
        } catch (IllegalStateException e) {
            // 회원가입 시 중복 회원 가입 예외가 발생하면 에러 메시지 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // 성공적으로 회원가입이 되면 201 Created 상태를 반환
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

