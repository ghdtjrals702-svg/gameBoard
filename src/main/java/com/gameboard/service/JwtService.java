package com.gameboard.service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    static final String PREFIX = "Bearer ";
    static final long EXPIRATIONTIME = 24 * 60 * 60 * 1000;
    static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SIGNING_KEY)
                .compact();
    }

    public String parseToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(PREFIX)) {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build();
            String username = jwtParser.parseClaimsJws(header.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (username != null) {
                return username;
            }
        }
        return null;
    }
}



    //쿠키로 하는거 나중에 다시 공부해볼것. 쿠키 삭제가 안 된다

//    static final String PREFIX = "Bearer ";
//
//    @Value("${jwt.secret}") // 환경변수에서 가져오는 문법
//    private String jwtSecret;
//
//    @Value("${jwt.expiration}") // 환경변수에서 가져오는 문법
//    private long expirationTime;
//
//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String parseToken(HttpServletRequest request) {
//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (header != null && header.startsWith(PREFIX)) {
//            JwtParser parser = Jwts.parserBuilder()
//                    .setSigningKey(getSigningKey())
//                    .build();
//            return parser.parseClaimsJws(header.replace(PREFIX, ""))
//                    .getBody()
//                    .getSubject();
//        }
//        return null;
//    }
//}
