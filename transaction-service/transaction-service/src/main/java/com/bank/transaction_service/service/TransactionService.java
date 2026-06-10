package com.bank.transaction_service.service;

import com.bank.transaction_service.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction createTransaction(
            String transactionId,
            String senderAccount,
            String receiverAccount,
            BigDecimal amount);

    Transaction getTransaction(
            String transactionId);

    List<Transaction> getAllTransactions();

    String transferMoney(
            String senderAccount,
            String receiverAccount,
            BigDecimal amount);
}