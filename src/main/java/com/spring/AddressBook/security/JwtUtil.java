package com.spring.AddressBook.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your_super_secret_key_that_is_at_least_32_bytes_long";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // Token expires in 24 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // ✅ Use new signWith() method
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()  // ✅ Use new parserBuilder() method
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, String email) {
        try {
            Claims claims = extractClaims(token);
            return claims.getSubject().equals(email) && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
