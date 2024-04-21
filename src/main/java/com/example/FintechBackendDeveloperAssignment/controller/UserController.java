package com.example.FintechBackendDeveloperAssignment.controller;

import com.example.FintechBackendDeveloperAssignment.DTO.UserRegistrationDTO;
import com.example.FintechBackendDeveloperAssignment.model.User;
import com.example.FintechBackendDeveloperAssignment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors");
        }

        try {
            User user = userService.registerUser(registrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        try {
            User user = userService.loginUser(email, password);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
