package com.project.sns.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@AllArgsConstructor
public class JwtTokenUtils {



  public static String getUserName(String token, String key) {
    Claims claims = extractClaims(token, key);
    return claims.get("userName",String.class);
  }

  public static boolean isExpired(String token,String key) {
    Date expiredDate = extractClaims(token, key).getExpiration();
    return expiredDate.before(new Date());
  }

  public static Claims extractClaims(String token, String key) {
    String base64EncodedSecretKey = Encoders.BASE64.encode(key.getBytes(StandardCharsets.UTF_8));
    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedSecretKey));

    return Jwts.parser().
            verifyWith(secretKey).
            build().
            parseSignedClaims(token).
            getPayload();
  }
  public static String generateToken(String userName,String key, Long expiredTimeMs) {
    Claims claims = Jwts.claims().build();
    //claims.put("userName", userName);

    return Jwts.builder()
            .subject(userName)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiredTimeMs))
            .claim("userName",userName)
            .signWith(getKey(key))
            .compact();

  }

  public static Key getKey(String key) {
    //byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

    String base64EncodedSecretKey = Encoders.BASE64.encode(key.getBytes(StandardCharsets.UTF_8));
    byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
