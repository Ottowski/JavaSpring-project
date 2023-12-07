package com.example.individuellUppgift2.JWT;
import lombok.Data;
@Data
public class JWTRequest {
    private String username;
    private String password;
    public JWTRequest() {
    }
    public JWTRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}