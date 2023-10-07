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
}
