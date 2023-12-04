package com.example.individuellUppgift2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@ComponentScan(basePackages = "com.example.individuellUppgift2")
public class IndividuellUppgift2Application {
	public static void main(String[] args) {
		SpringApplication.run(IndividuellUppgift2Application.class, args);
	}
}

