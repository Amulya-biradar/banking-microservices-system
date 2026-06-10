package com.bank.transaction_service.repository;

import com.bank.transaction_service.entity.Transaction;

import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);

    Transaction findByTransactionId(
            String transactionId);

    List<Transaction> findAll();
}