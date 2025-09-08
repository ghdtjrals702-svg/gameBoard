package com.gameboard.config;

import com.gameboard.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 현재 요청의 경로를 가져옵니다.
        String path = request.getRequestURI();

        // 로그인 및 회원가입 경로는 필터를 건너뜁니다.
        if (path.startsWith("/user/login") || path.startsWith("/user/signUp")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken != null) {

            String username = jwtService.parseToken(request);

            if (username != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);



    }
}

