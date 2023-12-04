package com.example.individuellUppgift2.JWT;
import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        // Generate a random key
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);

        // Encode the key to Base64
        String base64Key = Base64.getEncoder().encodeToString(key);

        System.out.println("Generated Key: " + base64Key);
    }
}


