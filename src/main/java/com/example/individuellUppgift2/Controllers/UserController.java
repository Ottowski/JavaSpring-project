package com.example.individuellUppgift2.Controllers;
import com.example.individuellUppgift2.AppEntity.AppUser;
import com.example.individuellUppgift2.DTO.*;
import com.example.individuellUppgift2.JWT.JWTRequest;
import com.example.individuellUppgift2.JWT.JWTResponse;
import com.example.individuellUppgift2.Service.JWTService;
import com.example.individuellUppgift2.Service.FileService;
import com.example.individuellUppgift2.Service.FolderService;
import com.example.individuellUppgift2.Config.SecurityConfig;
import com.example.individuellUppgift2.Repository.AppUserRepository;
import com.example.individuellUppgift2.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
@RestController
@RequestMapping("/api")
public class UserController {
    // User repository for database operations.
    private final AppUserRepository appUserRepository;
    // Password encoder for secure password storage.
    private final PasswordEncoder passwordEncoder;
    // Logger for logging information.
    private final FolderService folderService;
    private final UserService userService;
    private final JWTService jwtTokenService;
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    // Constructor for UserController, injecting dependencies.
    public UserController(UserService userService, JWTService jwtTokenService, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, FolderService folderService, FileService fileService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.folderService = folderService;
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }
    // Endpoint for user folder. http://localhost:8082/api/folders
    @PostMapping("/folders")
    public ResponseEntity<String> createFolder(@RequestBody FolderDTO folderDTO) {
        try {
            // Get the username of the authenticated user
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();

            // Call the folder service to create a new folder for the user
            folderService.createFolder(username, folderDTO);

            return ResponseEntity.ok("Folder created successfully");
        } catch (Exception e) {
            log.error("An error occurred while creating the folder: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the folder: " + e.getMessage());
        }
    }
    // Endpoint to get a list of registered users. http://localhost:8082/api/users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        // Retrieve all users from the database
        List<AppUser> users = appUserRepository.findAll();

        // Convert the list of AppUser entities to a list of UserDTOs
        List<UserDTO> userDTOs = users.stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getPassword()))
                .collect(Collectors.toList());

        // Return the list of UserDTOs in the response
        return ResponseEntity.ok(userDTOs);
    }
    // Endpoint for user register. http://localhost:8082/api/register {
    //  "username": "test@test.com",
    //  "password": "test"
    //}

    @PostMapping("/register")
    public ResponseEntity<AppUser> createUserWithRole(@RequestBody RegisterDTO userRegistrationDTO) {
        AppUser savedUser = userService.registerUser(userRegistrationDTO);
        // Issue a JWT token and include it in the response headers
        var token = jwtTokenService.issueToken(userRegistrationDTO.getUsername(), userRegistrationDTO.toString());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(savedUser);
    }

    // Endpoint for user login. http://localhost:8082/api/login {
    //  "username": "test@test.com",
    //  "password": "test"
    //}
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JWTRequest JWTRequest) {
        JWTResponse response = userService.login(JWTRequest);
        System.out.println(response);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.getJwt())
                .body(response);
    }
}