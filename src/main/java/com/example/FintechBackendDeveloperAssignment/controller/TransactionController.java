package com.example.FintechBackendDeveloperAssignment.controller;

import com.example.FintechBackendDeveloperAssignment.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/api/transactions/transfer")
    public ResponseEntity<?> transferMoney(@RequestParam String senderEmail, @RequestParam String recipientEmail, @RequestParam double amount) {
        try {
            transactionService.transferMoney(senderEmail, recipientEmail, amount);
            return ResponseEntity.ok("Money transfer successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

