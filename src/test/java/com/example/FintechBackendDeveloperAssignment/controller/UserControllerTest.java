package com.example.FintechBackendDeveloperAssignment.controller;

import com.example.FintechBackendDeveloperAssignment.DTO.UserRegistrationDTO;
import com.example.FintechBackendDeveloperAssignment.model.User;
import com.example.FintechBackendDeveloperAssignment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
    }

    @Test
    public void testRegisterUser_SuccessfulRegistration() {

        BindingResult mockBindingResult = mock(BindingResult.class);

        when(mockBindingResult.hasErrors()).thenReturn(false);

        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setName("umair");
        registrationDTO.setEmail("umair@example.com");
        registrationDTO.setPassword("password");


        User user = new User();
        user.setName("Umair");
        user.setEmail("umair@example.com");

        when(userService.registerUser(registrationDTO)).thenReturn(user);

        ResponseEntity<?> response = userController.registerUser(registrationDTO, mockBindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testRegisterUser_ValidationErrors() {

        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = userController.registerUser(registrationDTO, result);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation errors", response.getBody());
    }

    @Test
    public void testRegisterUser_RegistrationError() {

        BindingResult mockBindingResult = mock(BindingResult.class);

        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setName("umair");
        registrationDTO.setEmail("umair@example.com");
        registrationDTO.setPassword("password");

        when(userService.registerUser(registrationDTO)).thenThrow(new RuntimeException("Email already exists"));

        ResponseEntity<?> response = userController.registerUser(registrationDTO, mockBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
    }

    @Test
    public void testLoginUser_SuccessfulLogin() {
        User user = new User();
        user.setName("umair");
        user.setEmail("umair@example.com");

        when(userService.loginUser("umair@example.com", "password")).thenReturn(user);

        ResponseEntity<?> response = userController.loginUser("umair@example.com", "password");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testLoginUser_UnsuccessfulLogin() {
        when(userService.loginUser("umair@example.com", "password")).thenThrow(new RuntimeException("Invalid email or password"));

        ResponseEntity<?> response = userController.loginUser("umair@example.com", "password");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody());
    }
}

