package com.ysshop.shop.service;

import com.ysshop.shop.entity.User;
import com.ysshop.shop.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    public User createUser() {
        User user = new User();
        user.setUsername("kk1234");
        user.setName("kkt");
        user.setPasswordHashed("1234");

        return user;
    }

    @Test
    @DisplayName("save user test")
    public void saveUserTest() {
        User user = createUser();
        User savedUser = userService.saveUser(user);
        User findUser = userRepository.findByUsername("kk1234");

        assertThat(findUser).isSameAs(savedUser);
    }
}
