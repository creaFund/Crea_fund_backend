package com.creafund.creafund_api.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final Key jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // clé sécurisée générée automatiquement
    private final long jwtExpirationMs = 86400000; // 24h

    public String generateToken(String identifiant) {
        return Jwts.builder()
                .setSubject(identifiant)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(jwtKey) // Utilise la clé générée
                .compact();
    }

    public String getIdentifiantFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
