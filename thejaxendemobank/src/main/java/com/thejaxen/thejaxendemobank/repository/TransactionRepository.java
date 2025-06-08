package com.thejaxen.thejaxendemobank.repository;

import com.thejaxen.thejaxendemobank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
