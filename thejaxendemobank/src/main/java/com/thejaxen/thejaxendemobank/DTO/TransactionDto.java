package com.thejaxen.thejaxendemobank.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;


    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
