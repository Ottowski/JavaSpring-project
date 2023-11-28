package com.example.individuellUppgift2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        log.info("Received registration request for username: {}", userDTO.getUsername());
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            log.warn("Username already exists: {}", userDTO.getUsername());
            return ResponseEntity.badRequest().body("Username already exists");
        }
        AppUser newUser = new AppUser(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(newUser);
        log.info("User registered successfully");
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        log.info("Received login request for username: {}", userDTO.getUsername());
        Optional<AppUser> optionalUser = userRepository.findByUsername(userDTO.getUsername());
        AppUser user = new AppUser();
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                log.info("Login successful");

                // Create a UserDTO with active id and username
                UserDTO loggedInUserDTO = new UserDTO(user.getId(), user.getUsername());

                return ResponseEntity.ok(loggedInUserDTO);
            } else {
                log.warn("Incorrect password for user: {}", userDTO.getUsername());
                return ResponseEntity.badRequest().body(new UserDTO(user.getId(), "Incorrect password"));
            }
        } else {
            log.warn("User not found: {}", userDTO.getUsername());
            return ResponseEntity.badRequest().body(new UserDTO(user.getId(), "User not found"));
        }
    }
}
