package com.thejaxen.thejaxendemobank.service;

import com.thejaxen.thejaxendemobank.entity.Transaction;
import com.thejaxen.thejaxendemobank.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Service
public class BankStatement {

    //retrieve list of transactions within a date range given an account number

    private final TransactionRepository transactionRepository;

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;


        LocalDateTime start = LocalDate.parse(startDate, formatter).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate, formatter).atTime(LocalTime.MAX);

        return transactionRepository.findByAccountNumberAndCreatedAtBetween(accountNumber, start, end);
    }
}
