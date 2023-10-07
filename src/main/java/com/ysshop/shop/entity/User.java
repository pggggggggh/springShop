package com.ysshop.shop.entity;

import com.ysshop.shop.constant.Role;
import com.ysshop.shop.dto.NewUserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; // PK

    @Column(unique = true)
    private String username; // 로그인할 때 쓰는 아이디

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String passwordHashed; // 암호화된 비밀번호

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_" + role.toString();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return passwordHashed;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User createUser(NewUserDto newUserDto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setName(newUserDto.getName());
        user.setUsername(newUserDto.getUsername());
        user.setPasswordHashed(passwordEncoder.encode(newUserDto.getPassword()));
        user.setRole(newUserDto.getIsSeller() ? Role.SELLER : Role.USER);

        return user;
    }
}