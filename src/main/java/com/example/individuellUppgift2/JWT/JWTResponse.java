package com.example.individuellUppgift2.JWT;
import com.example.individuellUppgift2.DTO.UserDTO;
import lombok.Data;
@Data
public class JWTResponse {
    private final String jwt;
    private final UserDTO user;
    public JWTResponse(String jwt, UserDTO user) {
        this.jwt = jwt;
        this.user = user;
    }
}