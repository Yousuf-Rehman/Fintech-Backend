package com.example.FintechBackendDeveloperAssignment.service;

import com.example.FintechBackendDeveloperAssignment.DTO.UserRegistrationDTO;
import com.example.FintechBackendDeveloperAssignment.model.User;
import com.example.FintechBackendDeveloperAssignment.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Logger logger) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.logger = logger;
    }

    public User registerUser(UserRegistrationDTO registrationDTO) {
        User existingUser = userRepository.findByEmail(registrationDTO.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(registrationDTO.getName());
        user.setEmail(registrationDTO.getEmail());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userRepository.save(user);

        logger.info("User registered: " + user.getEmail());

        return user;
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        logger.info("User logged in: " + user.getEmail());

        return user;
    }
}
