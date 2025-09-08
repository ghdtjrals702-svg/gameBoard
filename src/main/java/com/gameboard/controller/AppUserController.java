package com.gameboard.controller;

import com.gameboard.domain.AppUserRepository;
import com.gameboard.domain.dto.AppUserDto;
import com.gameboard.service.AppUserService;
import com.gameboard.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class AppUserController {

    private final AppUserService appUserService;
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    @GetMapping("/check-username")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
        boolean exists = appUserRepository.existsByUsername(username);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<Map<String, Boolean>> checkNickname(@RequestParam String nickname) {
        boolean exists = appUserRepository.existsByNickname(nickname);
        return ResponseEntity.ok(Map.of("exists", exists));
    }


    @PostMapping(value = "/signUp")
    public AppUserDto addUser(@RequestBody AppUserDto appUserDto) throws Exception {
        return appUserService.addUser(appUserDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login (@RequestBody AppUserDto appUserDto, HttpServletResponse response) throws Exception {
        try {
            AppUserDto result = appUserService.login(appUserDto);
            String token = jwtService.generateToken(result.getUsername());
            // JWT를 쿠키에 담기
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(false)       // JS에서 접근 불가, XSS 공격 방어
                    .secure(false)        // https 환경이면 true
                    .path("/")            // 모든 경로에서 사용 가능
                    .maxAge(60 * 30) // 30분
                    .sameSite("Strict")   // CSRF 방어
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // 바디에 에러 메세지 넣어 줌
        }
        return ResponseEntity.ok("success");
    }

    /**
     * jwt 인증이 끝나면 username을 서버에 가지고 있는다. 그 username을 이용해서 사용자 정보를 조회하고 반환한다.
     * @param username
     * @return
     */
    @GetMapping(value = "/authentication")
    public ResponseEntity<AppUserDto> authentication(@AuthenticationPrincipal String username) throws Exception {
        return ResponseEntity.ok(appUserService.findUserInfomatiton(username));
    }
}
