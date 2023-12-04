package com.example.individuellUppgift2.Service;

import com.example.individuellUppgift2.AppEntity.AppUser;
import com.example.individuellUppgift2.DTO.AuthenticationRequest;
import com.example.individuellUppgift2.DTO.AuthenticationResponse;
import com.example.individuellUppgift2.DTO.RegistrationUserDTO;
import com.example.individuellUppgift2.DTO.UserDTO;
import com.example.individuellUppgift2.JWT.JWTService;
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


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                       AuthenticationManager authenticationManager, JWTService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public AppUser registerUser(RegistrationUserDTO userDto) {
        AppUser user = new AppUser();
        user.setUsername(userDto.getUsername());
        user.setRoles(userDto.getRoles());;
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public Optional<AppUser> loginUser(String username, String password) {
        Optional<AppUser> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
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
        userDto.setRoles(user.getRoles());

        String jwt = jwtService.generateToken(userDto, userDto.getRoles());
        return new AuthenticationResponse(jwt, userDto);
    }
}

