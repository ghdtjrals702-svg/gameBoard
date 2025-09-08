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
        if (path.startsWith("/api/check-username") || path.startsWith("/api/check-nickname") || path.startsWith("/api/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 필터 ==> 요청, 응답을 중간에서 가로채고 필요한 동작 수행
        //1. 요청 헤더에서 jwt 토큰을 꺼낸다.
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken != null) { //토큰이 비어있지 않으면
            //2. 꺼낸 토큰에서 유저 정보를 추출
            String username = jwtService.parseToken(request);

            //3. 추출된 유저 정보로 Authentication을 만들어서 SecurityContext 에 set (토큰 제거하고 나면 유저정보가 없음에도 정상처리를 하여서 수정을 조금 함)
            //토큰이 없는 요청이 오면 parse토큰하면 유저네임이 비어있음
            if (username != null) { //유저 정보가 정상적으로 파싱되어야 인증정보 담게 하는것
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null, //패스워드는 안 들어가도 되기때문에 널이다?
                        Collections.emptyList()); //빈 리스트 원래는 권한 자리인데 비우고 만들어준다
                SecurityContextHolder.getContext().setAuthentication(authentication);  //셋 해줌
            }
        }

        filterChain.doFilter(request, response);


        // username 어쩌고 하는데 앞에 필요함 Security config에 추가함
    }
}

