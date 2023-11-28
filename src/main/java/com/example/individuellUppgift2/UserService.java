// UserService class handles user registration and login logic.
package com.example.individuellUppgift2;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    // Password encoder for secure password storage.
    private final PasswordEncoder passwordEncoder;

    // Repository for database operations related to user entities.
    private final UserRepository userRepository;

    // Constructor for UserService, injecting dependencies.
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // Register a new user using the provided UserDTO.
    public AppUser registerUser(UserDTO userDTO) {
        // Create a new user entity.
        AppUser newUser = new AppUser();

        // Set username and encode the password.
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Save the new user to the database.
        return userRepository.save(newUser);
    }

    // Attempt to log in a user using the provided credentials.
    public Optional<AppUser> loginUser(String username, String password) {
        // Find user by username in the database.
        Optional<AppUser> user = userRepository.findByUsername(username);

        // Check if the user exists and the password matches.
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }

        // Return empty optional if login fails.
        return Optional.empty();
    }
}