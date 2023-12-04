package com.example.individuellUppgift2;

import com.example.individuellUppgift2.AppEntity.AppUser;
import com.example.individuellUppgift2.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID; // Import UUID class
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));

        // Check if the userId is already set; if not, set it
        if (appUser.getUserId() == null) {
            appUser.setUserId(generateUserId());
            userRepository.save(appUser); // Save the updated AppUser entity
        }

        return appUser;
    }


    private String generateUserId() {
        // Implement your logic to generate a unique user ID
        return UUID.randomUUID().toString();
    }
}
