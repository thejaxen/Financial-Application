package com.thejaxen.thejaxendemobank.controller;


import com.thejaxen.thejaxendemobank.entity.Transaction;
import com.thejaxen.thejaxendemobank.service.BankStatement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/bankStatement")
public class TransactionController {

    private BankStatement bankStatement;

    @GetMapping("/transactionality")
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate) {
        return bankStatement.generateStatement(accountNumber, startDate, endDate);
    }
}
