package com.example.individuellUppgift2.Service;
import com.example.individuellUppgift2.AppEntity.AppUser;
import com.example.individuellUppgift2.JWT.JWTRequest;
import com.example.individuellUppgift2.JWT.JWTResponse;
import com.example.individuellUppgift2.DTO.RegisterDTO;
import com.example.individuellUppgift2.DTO.UserDTO;
import com.example.individuellUppgift2.Repository.AppUserRepository;
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
    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public UserService(PasswordEncoder passwordEncoder, AppUserRepository appUserRepository,
                       AuthenticationManager authenticationManager, JWTService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    public AppUser registerUser(RegisterDTO userDto) {
        AppUser user = new AppUser();
        user.setUsername(userDto.getUsername());
        user.setRoles(userDto.getRoles());;
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return appUserRepository.save(user);
    }
    public Optional<AppUser> loginUser(String username, String password) {
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
    public JWTResponse login(JWTRequest JWTRequest) {
        return getAuthenticationResponse(JWTRequest);
    }
    private JWTResponse getAuthenticationResponse(JWTRequest JWTRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        JWTRequest.getUsername(),
                        JWTRequest.getPassword()
                )
        );
        AppUser user = (AppUser) authentication.getPrincipal();
        UserDTO userDto = new UserDTO();
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles());

        String jwt = jwtService.generateToken(userDto, userDto.getRoles());
        return new JWTResponse(jwt, userDto);
    }
}