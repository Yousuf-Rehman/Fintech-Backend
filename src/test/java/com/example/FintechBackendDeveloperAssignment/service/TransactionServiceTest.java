package com.example.FintechBackendDeveloperAssignment.service;

import com.example.FintechBackendDeveloperAssignment.model.Transaction;
import com.example.FintechBackendDeveloperAssignment.model.User;
import com.example.FintechBackendDeveloperAssignment.repository.TransactionRepository;
import com.example.FintechBackendDeveloperAssignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private Logger logger;

    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionService(userRepository, transactionRepository, logger);
    }

    @Test
    public void testTransferMoney_SenderRecipientFound() {

        User sender = new User();
        sender.setEmail("sender@example.com");
        sender.setBalance(100.0);

        User recipient = new User();
        recipient.setEmail("recipient@example.com");
        recipient.setBalance(50.0);

        when(userRepository.findByEmail("sender@example.com")).thenReturn(sender);
        when(userRepository.findByEmail("recipient@example.com")).thenReturn(recipient);


        transactionService.transferMoney("sender@example.com", "recipient@example.com", 25.0);


        assertEquals(75.0, sender.getBalance());
        assertEquals(75.0, recipient.getBalance());
        verify(userRepository, times(2)).save(any(User.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(logger, times(1)).info("Money transferred from sender@example.com to recipient@example.com, Amount: 25.0");
    }

    @Test
    public void testTransferMoney_SenderNotFound() {

        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            transactionService.transferMoney("sender@example.com", "recipient@example.com", 25.0);
        });
        verify(userRepository, never()).save(any(User.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(logger, never()).info(anyString());
    }

    @Test
    public void testTransferMoney_InsufficientFunds() {
        User sender = new User();
        sender.setEmail("sender@example.com");
        sender.setBalance(20.0);

        when(userRepository.findByEmail("sender@example.com")).thenReturn(sender);

        assertThrows(RuntimeException.class, () -> {
            transactionService.transferMoney("sender@example.com", "recipient@example.com", 25.0);
        });
        verify(userRepository, never()).save(any(User.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(logger, never()).info(anyString());
    }
}


