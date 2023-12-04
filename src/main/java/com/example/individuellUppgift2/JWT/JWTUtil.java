package com.example.individuellUppgift2.JWT;

import com.example.individuellUppgift2.DTO.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//import lombok.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.List;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTUtil {
    // Use a secure key for HS256
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.algorithm}")
    private String jwtAlgorithm;

    public String issueToken(String subject, String... scopes) {
        return issueTokens(subject, Map.of("scopes", scopes));
    }

    public String generateToken(UserDTO userDTO) {
        return issueTokens(userDTO.getUsername(), Map.of());
    }

    private String issueTokens(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, java.time.temporal.ChronoUnit.DAYS)))
                .signWith(SECRET_KEY, SignatureAlgorithm.forName(jwtAlgorithm))
                .compact();
    }

    public String issueToken(String subject, List<String> scopes) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("scopes", scopes)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Get the claims from the token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getSubject(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(Date.from(Instant.now()));
    }
}
