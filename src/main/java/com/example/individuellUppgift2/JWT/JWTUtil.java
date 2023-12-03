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
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.List;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class JWTUtil {
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    // It allows us to issue a token with a subject and a set of claims.
    public String issueToken(String subject, String... scopes) {
        return issueTokens(subject, Map.of("scopes", scopes));
    }

    public String generateToken(UserDTO userDTO) {
        return issueTokens(userDTO.getUsername(), Map.of());
    }

    private String issueTokens(String subject, Map<String, Object> claims) {
        System.out.println("subject: → " + subject);
        System.out.println("claims: ✊ " + claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, DAYS)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public static String issueToken(String subject, List<String> scopes) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("scopes", scopes)
                .signWith(getKey())
                .compact();
    }

    // It allows us to validate a token and return the subject. (The subject is the username in our case.)
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extracts the subject from the token.
    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    // Generates a key for the token.
    private static SecretKey getKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Validates the token and checks if the subject matches the username in the UserDetails object.
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getSubject(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Checks if the token is expired.
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(Date.from(Instant.now()));
    }
}
