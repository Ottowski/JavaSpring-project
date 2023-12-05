package com.example.individuellUppgift2.DTO;
import lombok.Getter;
import lombok.Setter;
import java.util.Collection;
import java.util.List;
@Getter
@Setter
public class UserDTO {
    private int id;
    private String username;
    private Collection<String> roles;
    public UserDTO() {
    }
    public UserDTO(int id, String username, Collection<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
    public UserDTO(int id, String username, String password) {
    }
    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", role='" + roles + '\'' +
                '}';
    }
    public String getUsername() {
        return username;
    }
    public List<String> getRoles() {
        return (List<String>) roles;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }
}
