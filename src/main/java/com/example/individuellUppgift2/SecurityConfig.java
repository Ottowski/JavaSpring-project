package com.example.individuellUppgift2;

import com.example.individuellUppgift2.JWT.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EntityScan("com.example.individuellUppgift2.AppEntity")
public class SecurityConfig {
    private final CustomUserDetailsService CustomUserDetailsService;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(CustomUserDetailsService CustomUserDetailsService, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.CustomUserDetailsService = CustomUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public AuthenticationProvider authenticationProvider(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
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
        http.csrf(csrf ->csrf.disable())
                .cors(withDefaults())
                .authorizeHttpRequests(configure -> configure
                        .requestMatchers(HttpMethod.POST, "/api/register","/api/login","/api/folders/createFolder","/api/files/upload").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/files/download", "api/users", "api/folders/all").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/files/delete").permitAll()
                        .anyRequest().permitAll())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider(CustomUserDetailsService, passwordEncoder()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}