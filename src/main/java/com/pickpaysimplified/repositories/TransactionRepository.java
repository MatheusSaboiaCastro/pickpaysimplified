package com.pickpaysimplified.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickpaysimplified.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // List<Transaction> findTransactionById(String transactionId);

    // List<Transaction> findTransactionByUserId(Long userId);

}
