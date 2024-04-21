package com.example.FintechBackendDeveloperAssignment.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {  // Me and yousaf are using this for testing purpose onlyu
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER");
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        try {
            return http
                    .authorizeHttpRequests(authCustomizer -> authCustomizer
                            .requestMatchers(HttpMethod.POST,
                                    "/api/users/register", "/api/users/login").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/api/transactions").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/api/transactions/{id}").hasRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/api/transactions/{id}").hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/transactions/{id}").hasRole("USER")
                    )
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
