package com.example.individuellUppgift2.DTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationUserDTO {
    private String username;
    private String password;

    public RegistrationUserDTO() {
    }

    public RegistrationUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegistrationUserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +

                '}';
    }
}

