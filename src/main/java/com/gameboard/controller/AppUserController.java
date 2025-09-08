package com.gameboard.controller;
import com.gameboard.domain.AppUserRepository;
import com.gameboard.domain.dto.AccountCredentials;
import com.gameboard.domain.dto.AppUserDto;
import com.gameboard.service.AppUserService;
import com.gameboard.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class AppUserController {
    private final AuthenticationManager authenticationManager;
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

    @PostMapping("/login") //로그인 할때 아이디랑 패스워드 필요함 그래서 디티오 만들어줌
    public ResponseEntity<?> login(@RequestBody AccountCredentials credentials) { //아이디 패스워드 묶어놓은 디티오
        //1. 유저의 ID/PW 정보를 기반으로 UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()); //아이디 패스워드 넣어서 인스턴스 생성
        //2. 생성된 UsernamePasswordAuthenticationToken을 authenticationManager 에게 전달
        //3. authenticationManager는 궁극적으로 UserDetailsService의 loadUserByUsername을 호출
        //4. 조회된 유저정보(UserDetail)와 UsernamePasswordAuthenticationToken을 비교해 인증 처리

        //2~4는 이 코드 치면 다 일어난다?
        Authentication authentication = authenticationManager.authenticate(token);

        //5. 최종 반환된 Authentication(인증된 유저 정보)를 기반으로 JWT TOKEN 발급
        String jwtToken = jwtService.generateToken(authentication.getName());

        //6. 컨트롤러는 응답 헤더(Authorization)에 Bearer <JWT TOKEN VALUE> 형태로 응답
        return ResponseEntity.ok() //바디에는 담을 거 없고 헤더에 뭘 담아줄거
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .build();
    }


    /**
     * jwt 인증이 끝나면 username을 서버에 가지고 있는다. 그 username을 이용해서 사용자 정보를 조회하고 반환한다.
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/authentication")
    public ResponseEntity<AppUserDto> authentication(@AuthenticationPrincipal String username) throws Exception {
        return ResponseEntity.ok(appUserService.findUserInfomatiton(username));
    }
}

//    @PostMapping(value = "/login")
//    public ResponseEntity<String> login (@RequestBody AppUserDto appUserDto, HttpServletResponse response) throws Exception {
//        try {
//            AppUserDto result = appUserService.login(appUserDto);
//            String token = jwtService.generateToken(result.getUsername());
//            // JWT를 쿠키에 담기
//            ResponseCookie cookie = ResponseCookie.from("jwt", token)
//                    .httpOnly(false)       // JS에서 접근 불가, XSS 공격 방어
//                    .secure(false)        // https 환경이면 true
//                    .path("/")            // 모든 경로에서 사용 가능
//                    .maxAge(60 * 30) // 30분
//                    .sameSite("Strict")   // CSRF 방어
//                    .build();
//
//            response.addHeader("Set-Cookie", cookie.toString());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // 바디에 에러 메세지 넣어 줌
//        }
//        return ResponseEntity.ok("success");
//    }


//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletResponse response) {
//        Cookie cookie = new Cookie("jwt", null);
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        return ResponseEntity.ok().build();
//    }
//}
