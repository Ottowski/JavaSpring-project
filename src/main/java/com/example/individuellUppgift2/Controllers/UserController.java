// UserController class handles HTTP requests related to user actions.
package com.example.individuellUppgift2.Controllers;

import com.example.individuellUppgift2.AppEntity.AppUser;
import com.example.individuellUppgift2.DTO.FolderDTO;
import com.example.individuellUppgift2.DTO.UserDTO;
import com.example.individuellUppgift2.Service.FileService;
import com.example.individuellUppgift2.Service.FolderService;
import com.example.individuellUppgift2.SecurityConfig;
import com.example.individuellUppgift2.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
@RestController
@RequestMapping("/api")
public class UserController {

    // User repository for database operations.
    private final UserRepository userRepository;

    // Password encoder for secure password storage.
    private final PasswordEncoder passwordEncoder;

    // Logger for logging information.

    private final FolderService folderService;

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    // Constructor for UserController, injecting dependencies.
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, FolderService folderService, FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.folderService = folderService;
    }

    // Endpoint for user folder. http://localhost:8080/api/folders
    @PostMapping("/folders")
    public ResponseEntity<String> createFolder(@RequestBody FolderDTO folderDTO) {
        // Get the username of the authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Call the folder service to create a new folder for the user
        folderService.createFolder(username, folderDTO.getFolderName());

        return ResponseEntity.ok("Folder created successfully");
    }

    // Endpoint for user register. http://localhost:8080/api/register {
    //  "username": "test@test.com",
    //  "password": "test"
    //}
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        // Log registration request.
        log.info("Received registration request for username: {}", userDTO.getUsername());

        // Check if the username already exists.
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            log.warn("Username already exists: {}", userDTO.getUsername());
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Create a new user and save to the database.
        AppUser newUser = new AppUser(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(newUser);

        // Log successful registration.
        log.info("User registered successfully");
        return ResponseEntity.ok("User registered successfully");
    }

    // Endpoint for user login. http://localhost:8080/api/login {
    //  "username": "test@test.com",
    //  "password": "test"
    //}
    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        // Log login request.
        log.info("Received login request for username: {}", userDTO.getUsername());

        // Find user by username in the database.
        Optional<AppUser> optionalUser = userRepository.findByUsername(userDTO.getUsername());
        AppUser user = new AppUser();

        // Check if the user exists.
        if (optionalUser.isPresent()) {
            user = optionalUser.get();

            // Check if the password matches.
            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                log.info("Login successful");

                // Create a UserDTO with active id, username, and password.
                UserDTO loggedInUserDTO = new UserDTO(user.getId(), user.getUsername(), user.getPassword());

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
