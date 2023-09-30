package com.ysshop.shop.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ysshop.shop.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        try {
            System.out.println("isAuthenticated: " + authResult.isAuthenticated());
            User user = (User) authResult.getPrincipal();
            String username = user.getUsername();
            Algorithm algorithm = Algorithm.HMAC256("ice2022");
            String accessToken = JWT.create()
                    .withIssuer("int-i")
                    .withSubject(username)
                    .sign(algorithm);
            System.out.println("Create JWT: user_id=" + username);
            response.addCookie(new Cookie("access_token", accessToken));
            response.getWriter().write(accessToken);
        } catch (JWTCreationException | IOException exception) {
            exception.printStackTrace();
        }
    }
}