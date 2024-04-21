package com.example.FintechBackendDeveloperAssignment.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderEmail;

    private String recipientEmail;

    private double amount;


}
