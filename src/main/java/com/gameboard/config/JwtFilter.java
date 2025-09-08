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

        String path = request.getRequestURI();
        if (path.startsWith("/api/check-username") ||
                path.startsWith("/api/check-nickname") ||
                path.startsWith("/api/signup") ||
                path.startsWith("/api/posts")) {
            filterChain.doFilter(request, response);
            return;
        }



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




        // 필터 ==> 요청, 응답을 중간에서 가로채고 필요한 동작 수행
        //1. 요청 헤더에서 jwt 토큰을 꺼낸다.

        // 현재 요청의 경로를 가져옵니다.



    }
}

