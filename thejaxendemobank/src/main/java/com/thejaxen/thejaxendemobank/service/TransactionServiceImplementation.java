package com.thejaxen.thejaxendemobank.service;

import com.thejaxen.thejaxendemobank.DTO.TransactionDto;
import com.thejaxen.thejaxendemobank.entity.Transaction;
import com.thejaxen.thejaxendemobank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplementation implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactiondto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactiondto.getTransactionType())
                .amount(transactiondto.getAmount())
                .accountNumber(transactiondto.getAccountNumber())
                .status("SUCCESS")
                .build();

        transactionRepository.save(transaction);
        System.out.println("Transaction saved successfully.");
    }
}
