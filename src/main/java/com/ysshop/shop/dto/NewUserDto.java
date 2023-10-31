package com.ysshop.shop.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
@ToString
public class NewUserDto {
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String username;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Length(min=8, max=24, message = "비밀번호는 8자 이상 24자 이하로 입력해주세요.")
    private String password;

    @NotNull
    private Boolean isSeller;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "휴대폰 번호를 입력해주세요.")
    private String phoneNumber;
}
