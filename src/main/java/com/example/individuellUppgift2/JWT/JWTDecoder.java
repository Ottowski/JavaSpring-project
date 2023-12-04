package com.example.individuellUppgift2.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JWTDecoder {
    public static void main(String[] args) {
        String token = "my_secret_is_a_secret";

        // Decode the token
        Jws<Claims> claimsJws = Jwts.parser().parseClaimsJws(token);

        // Get header, body (claims), and signature
        System.out.println("Header: " + claimsJws.getHeader());
        System.out.println("Claims: " + claimsJws.getBody());
        System.out.println("Signature: " + claimsJws.getSignature());
    }
}

