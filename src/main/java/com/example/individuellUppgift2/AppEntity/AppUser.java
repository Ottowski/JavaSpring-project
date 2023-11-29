// AppUser class represents a user entity with UserDetails implementation.
package com.example.individuellUppgift2.AppEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "app_user")
public class AppUser /*implements UserDetails*/ {

    // User ID.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // User username.
    @Column(name = "username", unique = true)
    private String username;

    // User password.
    @Column(name = "password")
    private String password;

    // Constructor with username and password.
    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Implementation of UserDetails methods.
    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/
}