package com.ysshop.shop.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ysshop.shop.entity.User;
import com.ysshop.shop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtDecodeFilter extends OncePerRequestFilter {
    private final UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        if (request.getMethod().equals("OPTIONS")) {
//            return true;
//        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                String accessToken = header.substring(7);
                Algorithm algorithm = Algorithm.HMAC256("ice2022");
                JWTVerifier verifier = JWT.require(algorithm).withIssuer("int-i").build();
                DecodedJWT jwt = verifier.verify(accessToken);
                String username = jwt.getSubject();
                System.out.println("Verify JWT: user_id=" + username);
                User user = (User) userService.loadUserByUsername(username);
                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (JWTVerificationException exception) {
                exception.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}