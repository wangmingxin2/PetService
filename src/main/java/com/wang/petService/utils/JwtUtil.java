package com.wang.petService.utils;

import com.wang.petService.interceptor.JwtAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private String secret = "wangMingXin"; // Use a strong secret key
    private long expiration = 1000 * 60 *30; // 30 minutes

    public String getToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration ))//半小时
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Map<String, Object> extractClaims(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            return claims;
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException("Token has expired", e);
        } catch (SignatureException e) {
            throw new JwtAuthenticationException("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            throw new JwtAuthenticationException("Invalid JWT token", e);
        } catch (UnsupportedJwtException e) {
            throw new JwtAuthenticationException("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT claims string is empty", e);
        }
    }
}