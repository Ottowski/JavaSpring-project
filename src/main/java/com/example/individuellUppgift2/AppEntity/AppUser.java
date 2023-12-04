package com.example.individuellUppgift2.AppEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
//@NoArgsConstructor
@Getter
@Setter
@Table(name = "app_user")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "accountNonLocked")
    private boolean accountNonLocked;

    @Column(name = "user_id", nullable = false)
    private String userId;  // Change the type to String


    public AppUser() {
        // default constructor
    }

    public AppUser(String username, String password, boolean accountNonLocked) {
        this.username = username;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Change to your logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Change to your logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Change to your logic if needed
    }
}
