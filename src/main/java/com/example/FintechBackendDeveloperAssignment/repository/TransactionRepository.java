package com.example.FintechBackendDeveloperAssignment.repository;

import com.example.FintechBackendDeveloperAssignment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

