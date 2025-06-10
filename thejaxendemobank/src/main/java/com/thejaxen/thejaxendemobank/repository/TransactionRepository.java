package com.thejaxen.thejaxendemobank.repository;

import com.thejaxen.thejaxendemobank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
        List<Transaction> findByAccountNumberAndCreatedAtBetween(String accountNumber, LocalDateTime start, LocalDateTime end);
}
