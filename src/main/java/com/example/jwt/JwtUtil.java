package com.example.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    // 토큰 생성용 보안키
    private final String SECRETKEY = "fekjkfe43jfe";

    // 정보 추출용 메소드
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    // 토큰 생성(아이디 정보를 이용한 토큰 생성)
    // 아이디만 포함. json -> string -> 토큰 -> string -> json
    // { "userid" : "aaa", "role" : "CUSTOMER" }
    public String generateToken(String username, String role) {
        System.out.println(username);

        // 1. jsonobject로 변환
        JSONObject jobj = new JSONObject();
        jobj.put("userid", username);
        jobj.put("role", role);

        
        // ex) 30분 => 1000 * 60 * 30
        long tokenValidTime = 1000 * 60 * 60 * 4; // 4시간


        // 문자형태로  추가한 후 토큰 생성
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder().setClaims(claims).setSubject(jobj.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRETKEY)
                .compact();
        return token;
    }

    // 토큰 검증
    public Boolean validateToken(String token, String userid) {
        // 토큰에서 아이디 정보 추출
        final String username = this.extractUsername(token);
        if (username.equals(userid) && !isTokenExpired(token)) {
            return true;
        }
        return false;
    }

    // 토큰에서 아이디 정보 추출하기
    public String extractUsername(String token) {
        String sObj = extractClaim(token, Claims::getSubject);
        JSONObject jobj = new JSONObject(sObj);
        System.out.println(jobj.toString());
        return jobj.getString("userid");
    }

    // 토큰에서 권한 추출하기
    public String extractUserRole(String token) {
        String sObj = extractClaim(token, Claims::getSubject);
        JSONObject jobj = new JSONObject(sObj);
        return jobj.getString("role");
    }

    // 토큰에서 만료 시간 추출하기
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰의 만료시간이 유효한지 확인
    public Boolean isTokenExpired(String token) {
        // 만료시간 가져와서 현재시간보다 이전인지 확인
        return this.extractExpiration(token).before(new Date());
    }

}
