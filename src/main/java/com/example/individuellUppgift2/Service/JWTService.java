package com.example.individuellUppgift2.Service;
import com.example.individuellUppgift2.DTO.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;
@Service
public class JWTService {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String issueToken(String subject) {
        return issueTokens(subject, Map.of());
    }
    public String issueToken(String subject, String... scoops) {
        return issueTokens(subject, Map.of("scoops", scoops));
    }
    public String generateToken(UserDTO userDto, List<String> roles) {
        return issueTokens(userDto.getUsername(), Map.of("roles", roles));
    }
    private String issueTokens(String subject, Map<String, Object> claims) {
        System.out.println("Generating Token - Subject: " + subject + ", Claims: " + claims);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(4, DAYS)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        System.out.println("Generated Token: " + token);
        return token;
    }
    private Claims getClaims(String token) {
        try {
            System.out.println("Received Token: " + token);
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.err.println("Error parsing JWT: " + e.getMessage());
            throw e;
        }
    }
    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }
    public static Key getKey() {
        return SECRET_KEY;
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getSubject(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(Date.from(Instant.now()));
    }
}