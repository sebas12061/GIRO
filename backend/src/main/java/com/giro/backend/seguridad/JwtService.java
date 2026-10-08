package com.giro.backend.seguridad;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMillis;

    public JwtService(
        @Value("${giro.security.jwt.secret}") String secret,
        @Value("${giro.security.jwt.expiration-ms:3600000}") long expirationMillis
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(Usuario usuario) {
        Date now = new Date();
        return Jwts.builder()
            .subject(usuario.getUsername())
            .claim("usuarioId", usuario.getId())
            .claim("restauranteId", usuario.getRestauranteId())
            .issuedAt(now)
            .expiration(new Date(now.getTime() + expirationMillis))
            .signWith(signingKey)
            .compact();
    }

    public String extractUsername(String token) {
        return parse(token).getSubject();
    }

    public UUID extractRestauranteId(String token) {
        return UUID.fromString(parse(token).get("restauranteId", String.class));
    }

    public boolean isValid(String token, UserDetails user) {
        Claims claims = parse(token);
        return claims.getSubject().equals(user.getUsername()) && claims.getExpiration().after(new Date());
    }

    private Claims parse(String token) {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }
}
