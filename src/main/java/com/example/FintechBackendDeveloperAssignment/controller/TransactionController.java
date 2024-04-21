package com.example.FintechBackendDeveloperAssignment.controller;

import com.example.FintechBackendDeveloperAssignment.model.Transaction;
import com.example.FintechBackendDeveloperAssignment.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "post", description = "Post methods of Transaction APIs")
    @Operation(summary = "Transfer money",
            description = "This APi is use to transfer money from one user account to other user account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Transaction.class)) }),
            @ApiResponse(responseCode = "404", description = "Transaction in complete",
                    content = @Content) })
    @PostMapping("/api/transactions/transfer")
    public ResponseEntity<?> transferMoney(@Parameter(
            description = "senders Email",
            required = true)@RequestParam String senderEmail, @Parameter(
            description = "recipients Email",
            required = true)@RequestParam String recipientEmail, @Parameter(
            description = "amount to be transfered",
            required = true)@RequestParam double amount) {
        try {
            transactionService.transferMoney(senderEmail, recipientEmail, amount);
            return ResponseEntity.ok("Money transfer successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

