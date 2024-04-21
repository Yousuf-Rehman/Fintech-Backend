package com.example.FintechBackendDeveloperAssignment.service;

import com.example.FintechBackendDeveloperAssignment.model.Transaction;
import com.example.FintechBackendDeveloperAssignment.model.User;
import com.example.FintechBackendDeveloperAssignment.repository.TransactionRepository;
import com.example.FintechBackendDeveloperAssignment.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final Logger logger;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository, Logger logger) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.logger = logger;
    }

    @Async
    @Transactional
    public void transferMoney(String senderEmail, String recipientEmail, double amount) {
        User sender = userRepository.findByEmail(senderEmail);
        User recipient = userRepository.findByEmail(recipientEmail);

        if (sender == null || recipient == null) {
            throw new RuntimeException("Sender or recipient not found");
        }

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(recipient);

        // Record the transaction
        Transaction transaction = new Transaction();
        transaction.setSenderEmail(senderEmail);
        transaction.setRecipientEmail(recipientEmail);
        transaction.setAmount(amount);
        transactionRepository.save(transaction);

        logger.info("Money transferred from " + senderEmail + " to " + recipientEmail + ", Amount: " + amount);
    }
}
