package com.example.individuellUppgift2.Service;
import com.example.individuellUppgift2.AppEntity.AppUser;
import com.example.individuellUppgift2.DTO.AuthenticationRequest;
import com.example.individuellUppgift2.DTO.AuthenticationResponse;
import com.example.individuellUppgift2.DTO.RegistrationUserDTO;
import com.example.individuellUppgift2.DTO.UserDTO;
import com.example.individuellUppgift2.JWT.JWTUtil;
import com.example.individuellUppgift2.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    // Password encoder for secure password storage.
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                       AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    // Register a new user using the provided UserDTO.
    public AppUser registerUser(RegistrationUserDTO userDTO) {
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
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();

    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        return getAuthenticationResponse(authenticationRequest);
    }

    private AuthenticationResponse getAuthenticationResponse(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        AppUser user = (AppUser) authentication.getPrincipal();
        UserDTO userDto = new UserDTO();
        userDto.setUsername(user.getUsername());

        String jwt = jwtUtil.generateToken(userDto);
        return new AuthenticationResponse(jwt, userDto);
    }
}