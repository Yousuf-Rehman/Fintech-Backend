package com.example.FintechBackendDeveloperAssignment.service;

import com.example.FintechBackendDeveloperAssignment.DTO.UserRegistrationDTO;
import com.example.FintechBackendDeveloperAssignment.model.User;
import com.example.FintechBackendDeveloperAssignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private Logger logger;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder, logger);
    }

    @Test
    public void testRegisterUser_NewUser() {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setName("John Doe");
        registrationDTO.setEmail("john@example.com");
        registrationDTO.setPassword("password");

        when(userRepository.findByEmail("john@example.com")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User registeredUser = userService.registerUser(registrationDTO);

        assertNotNull(registeredUser);
        assertEquals("John Doe", registeredUser.getName());
        assertEquals("john@example.com", registeredUser.getEmail());
        assertEquals("USER", registeredUser.getRole());
        assertEquals("encodedPassword", registeredUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(logger, times(1)).info("User registered: john@example.com");
    }

    @Test
    public void testRegisterUser_EmailExists() {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setEmail("john@example.com");

        when(userRepository.findByEmail("john@example.com")).thenReturn(new User());

        assertThrows(RuntimeException.class, () -> {
            userService.registerUser(registrationDTO);
        });
        verify(userRepository, never()).save(any(User.class));
        verify(logger, never()).info(anyString());
    }

    @Test
    public void testLoginUser_ValidCredentials() {
        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        User loggedInUser = userService.loginUser("john@example.com", "password");

        assertNotNull(loggedInUser);
        assertEquals("john@example.com", loggedInUser.getEmail());
        verify(logger, times(1)).info("User logged in: john@example.com");
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            userService.loginUser("john@example.com", "password");
        });
        verify(logger, never()).info(anyString());
    }
}

