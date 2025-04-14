package com.pickpaysimplified.dtos;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long payerId, Long payeeId) {
    public TransactionDTO {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Value must be greater than zero.");
        }
        if (payerId == null || payeeId == null) {
            throw new IllegalArgumentException("Payer and Payee IDs must not be null.");
        }
    }

}
