package com.example.individuellUppgift2.Config;
import com.example.individuellUppgift2.Service.UserDetailsService;
import com.example.individuellUppgift2.Service.JWTService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.individuellUppgift2.JWT.JWTAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService UserDetailsService;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(UserDetailsService UserDetailsService, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.UserDetailsService = UserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(JWTService jwtService, org.springframework.security.core.userdetails.UserDetailsService userService) {
        return new JWTAuthenticationFilter(jwtService, userService);
    }
    @Bean
    public AuthenticationProvider authenticationProvider(@Qualifier("customUserDetailsService") org.springframework.security.core.userdetails.UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(configure -> configure
                        .requestMatchers(HttpMethod.POST, "/api/register","/api/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/folders/createFolder","/api/files/upload").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/api/files/download", "api/users", "api/folders/all").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/files/delete").hasAuthority("ROLE_USER")
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider(UserDetailsService, passwordEncoder()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}