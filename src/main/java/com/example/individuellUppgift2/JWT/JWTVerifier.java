package com.example.individuellUppgift2.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTVerifier {
    public static void main(String[] args) {
        String secretKey = "my_secret_is_a_secret"; // Replace with your actual secret key
        String token = "your-jwt-token-here";

        try {
            Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).parseClaimsJws(token);
            System.out.println("Signature is valid.");
        } catch (Exception e) {
            System.out.println("Signature verification failed.");
            e.printStackTrace();
        }
    }
}

