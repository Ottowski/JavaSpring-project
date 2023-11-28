package com.example.individuellUppgift2;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // CustomUserDetailsService for loading user details.
    private final CustomUserDetailsService CustomUserDetailsService;
    // Constructor for SecurityConfig, injecting dependencies.
    public SecurityConfig(CustomUserDetailsService CustomUserDetailsService) {
        this.CustomUserDetailsService = CustomUserDetailsService;
    }

    // Authentication provider for DaoAuthenticationProvider.
    @Bean
    public AuthenticationProvider authenticationProvider(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        // Create DaoAuthenticationProvider.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Set password encoder and user details service
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        // Return the configured authentication provider.
        return provider;
    }

    // Password encoder for secure password storage.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCryptPasswordEncoder for password encoding.
        return new BCryptPasswordEncoder();
    }

    // Configure the default security filter chain.
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // Configure security settings.
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())

                // Allow unauthenticated access to registration and login endpoints.
                .authorizeHttpRequests(configure -> configure
                        .requestMatchers(HttpMethod.POST,"/api/register","/api/login","api/folders", "/api/files/upload").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/files/download").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/files/delete").authenticated()
                        // Require authentication for all other requests.
                        .anyRequest().authenticated())

                // Configure session management.
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set the authentication provider.
                .authenticationProvider(authenticationProvider(CustomUserDetailsService, passwordEncoder()));

        // Build and return the configured security filter chain.
        return http.build();
    }


    // Create an AuthenticationManager bean.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Return the configured AuthenticationManager.
        return configuration.getAuthenticationManager();
    }


}
