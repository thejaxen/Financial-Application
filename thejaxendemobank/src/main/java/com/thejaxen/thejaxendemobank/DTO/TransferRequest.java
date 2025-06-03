package com.thejaxen.thejaxendemobank.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TransferRequest {

    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal amount;

    public String sourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String destinationAccountNumber() {
        return destinationAccountNumber;
    }

    public BigDecimal amount() {
        return amount;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
