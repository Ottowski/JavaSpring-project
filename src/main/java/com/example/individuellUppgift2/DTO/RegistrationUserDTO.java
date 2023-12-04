package com.example.individuellUppgift2.DTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class RegistrationUserDTO {
    private String username;
    private String password;
    private List<String> roles;


    public RegistrationUserDTO() {
    }

    public RegistrationUserDTO(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "RegistrationUserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + roles + '\'' +
                '}';
    }
}


