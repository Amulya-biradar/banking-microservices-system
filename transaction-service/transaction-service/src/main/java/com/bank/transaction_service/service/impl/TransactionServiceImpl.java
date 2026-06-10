package com.bank.transaction_service.service.impl;

import com.bank.transaction_service.client.AccountClient;
import com.bank.transaction_service.dto.BalanceUpdateRequestDto;
import com.bank.transaction_service.entity.Transaction;
import com.bank.transaction_service.repository.TransactionRepository;
import com.bank.transaction_service.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
@Service
public class TransactionServiceImpl
        implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,AccountClient accountClient) {

        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
    }

    @Override
    @Transactional
    public Transaction createTransaction(
            String transactionId,
            String senderAccount,
            String receiverAccount,
            BigDecimal amount) {

        Transaction transaction =
                Transaction.builder()
                        .transactionId(transactionId)
                        .senderAccount(senderAccount)
                        .receiverAccount(receiverAccount)
                        .amount(amount)
                        .status("SUCCESS")
                        .build();

        transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction getTransaction(
            String transactionId) {

        return transactionRepository.findByTransactionId(
                transactionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();
    }
    @Override
    @Transactional
    @CircuitBreaker(
            name = "accountService",
            fallbackMethod = "transferFallback"
    )
    public String transferMoney(
            String senderAccount,
            String receiverAccount,
            BigDecimal amount) {

        accountClient.getAccount(senderAccount);

        accountClient.getAccount(receiverAccount);

        accountClient.debit(
                BalanceUpdateRequestDto.builder()
                        .accountNumber(senderAccount)
                        .amount(amount)
                        .build());

        accountClient.credit(
                BalanceUpdateRequestDto.builder()
                        .accountNumber(receiverAccount)
                        .amount(amount)
                        .build());

        Transaction transaction =
                Transaction.builder()
                        .transactionId(
                                UUID.randomUUID().toString())
                        .senderAccount(senderAccount)
                        .receiverAccount(receiverAccount)
                        .amount(amount)
                        .status("SUCCESS")
                        .build();

        transactionRepository.save(transaction);
        return "Transfer Successful";
    }
    @Transactional
    public String transferFallback(
            String senderAccount,
            String receiverAccount,
            BigDecimal amount,
            Exception ex) {

        System.out.println(
                "Circuit Breaker Activated: "
                        + ex.getMessage());

        Transaction transaction =
                Transaction.builder()
                        .transactionId("FAILED")
                        .senderAccount(senderAccount)
                        .receiverAccount(receiverAccount)
                        .amount(amount)
                        .status("ACCOUNT SERVICE UNAVAILABLE")
                        .build();

        transactionRepository.save(transaction);
        return "Account Service Unavailable";
    }
}