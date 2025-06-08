package com.thejaxen.thejaxendemobank.service;

import com.thejaxen.thejaxendemobank.DTO.TransactionDto;
import com.thejaxen.thejaxendemobank.entity.Transaction;
import org.springframework.stereotype.Service;


public interface TransactionService {

    void saveTransaction(TransactionDto transaction);
}
