package com.example.FintechBackendDeveloperAssignment.controller;

import com.example.FintechBackendDeveloperAssignment.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    private TransactionController transactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionController = new TransactionController(transactionService);
    }

    @Test
    public void testTransferMoney_SuccessfulTransfer() {
        doNothing().when(transactionService).transferMoney("sender@example.com", "recipient@example.com", 100.0);

        ResponseEntity<?> response = transactionController.transferMoney("sender@example.com", "recipient@example.com", 100.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Money transfer successful", response.getBody());
    }

    @Test
    public void testTransferMoney_RuntimeException() {
        doThrow(new RuntimeException("Invalid email")).when(transactionService).transferMoney(anyString(), anyString(), anyDouble());

        ResponseEntity<?> response = transactionController.transferMoney("sender@example.com", "recipient@example.com", 100.0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid email", response.getBody());
    }
}
