package com.project.sns.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@AllArgsConstructor
public class JwtTokenUtils {


  
  public static String generateToken(String userName,String key, Long expiredTimeMs) {
    Claims claims = Jwts.claims().build();
    //claims.put("userName", userName);

    return Jwts.builder()
            .subject(userName)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiredTimeMs))
            .signWith(getKey(key))
            .compact();

  }

  public static Key getKey(String key) {
    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
    //byte[] keyBytes = Decoders.BASE64.decode(key);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
