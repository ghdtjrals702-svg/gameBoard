package com.gameboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthEntryConfig authEntryConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        //인코딩 없이 진행
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerBuilder auth)
            throws Exception {

        http.csrf((csrf) -> csrf.disable());
        //http.cors(withDefaults());

        // 세션 관리 상태 없음으로 구성, Spring Security가 세션 생성 or 사용 X
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        // FormLogin, BasicHttp 비활성화
        http.formLogin((form) -> form.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 권한 규칙 작성
        //
//        http.authorizeHttpRequests(authorize -> authorize
//                .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
//                .requestMatchers(HttpMethod.POST,"/user/signUp").permitAll()
//                .anyRequest().authenticated())
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling((ex) ->
//                        ex.authenticationEntryPoint(authEntryConfig));
//        http.authorizeHttpRequests(authorize -> authorize
//                .anyRequest().permitAll());
//
//        http.authorizeHttpRequests(authorize -> authorize
//                .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
//                .requestMatchers(HttpMethod.POST,"/user/signUp").permitAll()
//                .anyRequest().authenticated())
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();  //exception 발생할 수도 있어서 떠넘긴다
        //AuthenticationManager 이 타입이 빈으로 관리된다.
    }


}
