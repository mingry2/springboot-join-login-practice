package com.springboot.joinpractice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    public static String createToken(String userName, String key, long expireTimeMs) {
        Claims claims = Jwts.claims(); // 일종의 map 으로 key, value 로 값을 담을 수 있음
        // claims에다가 userName을 넣어줌
        // token 을 열면 userName이 들어있을것임
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                // 만든날짜
                .setIssuedAt(new Date(System.currentTimeMillis())) // 지금시간
                // 끝나는날짜
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) // 지금시간 + 언제끝날지
                // 아래의 key를 가지고 SignatureAlgorithm.HS256 알고리즘을 이용하여 사인을 하겠다.
                .signWith(SignatureAlgorithm.HS256, key) // 뭘로 사인할지? 받은 key로 잠금한다.
                .compact();

    }
}
