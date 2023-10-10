package com.ysshop.shop.controller;
import com.ysshop.shop.repository.UserRepository;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping(value = "/new")                 // 회원가입 페이지로 이동할 수 있도록 메소드 작성
    public String userForm(Model model) {
        model.addAttribute("newUserDto", new NewUserDto());
        return "user/userForm";
    }

    @GetMapping(value = "/select")
    public String userSelect(Model model) {
        return "user/userTypeSelectForm";
        }
        @PostMapping("/new")
        public String newUser(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult, Model model) {
            if(bindingResult.hasErrors()) {         // 에러가 있다면 회원 가입 페이지로 이동
                return "user/userForm";
            }
            try {
                User user = User.createUser(newUserDto, passwordEncoder);
                userService.saveUser(user);
            } catch (IllegalStateException e) {
                model.addAttribute("errorMessage", e.getMessage());     // 회원 가입 시 중복 회원 가입 예외가 발생하면 에러 메시지를 뷰로 전달
                return "user/userForm";
            }

            return "redirect:/";
        }
    @GetMapping(value = "/login")
    public String loginMember() {
        return "/member/memberLoginForm";
    }


    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    // 회원 아이디 찾기
    @RequestMapping(value = "/findId", method = RequestMethod.POST)
    @ResponseBody
    public String findId(@RequestParam("userEmail") String userEmail) {
        String email = String.valueOf(userRepository.findByEmail(userEmail));
        System.out.println("회원 이메일 = " + email);
        if(email == null) {
            return null;
        } else {
            return email;
        }
    }

    // 회원 비밀번호 찾기
    @GetMapping(value = "/findUser")
    public String findUser(Model model) {
        return "/user/findUserForm";
    }

    // 비밀번호 찾기 시, 임시 비밀번호 담긴 이메일 보내기
    @Transactional
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("userEmail") String userEmail){
        MailDto dto = mailService.createMailAndChangePassword(userEmail);
        mailService.mailSend(dto);

        return "/user/userLoginForm";
    }

} // mailservice와 dto 구현예정입니다. 
