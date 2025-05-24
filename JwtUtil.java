package com.example.demo.Utilitarios;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    private String chave;
    private static Key secretKey;

    @Value("${jwt.secret}")
    public void setChave(String chave) {
        this.chave = chave;
        JwtUtil.secretKey = Keys.hmacShaKeyFor(this.chave.getBytes());
    }

    public static String generateToken(String username,String email) {
        int expirationTime = 1000 * 60 * 60; // 1 hora
        return Jwts.builder()
                .setSubject(username)
                .claim("email", email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 1 hora
                .signWith(JwtUtil.secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JwtUtil.secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public static String getTokenUser(String token) {
        return validateToken(token).getBody().getSubject();
    }

    public static String getTokenEmail(String token) {
        return validateToken(token).getBody().get("email", String.class);
    }
}
