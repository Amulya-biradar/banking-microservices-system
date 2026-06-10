package com.bank.transaction_service.controller;

import com.bank.transaction_service.client.AccountClient;
import com.bank.transaction_service.dto.AccountResponseDto;
import com.bank.transaction_service.dto.TransactionRequestDto;
import com.bank.transaction_service.dto.TransactionResponseDto;
import com.bank.transaction_service.dto.TransferRequestDto;
import com.bank.transaction_service.entity.Transaction;
import com.bank.transaction_service.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountClient accountClient;
     @Autowired
    public TransactionController(
            TransactionService transactionService,AccountClient accountClient) {

        this.transactionService = transactionService;
         this.accountClient = accountClient;
    }


    @GetMapping("/test/{accountNumber}")
    public AccountResponseDto test(
            @PathVariable String accountNumber) {

        return accountClient.getAccount(
                accountNumber);
    }

    @PostMapping
    public TransactionResponseDto createTransaction(
            @RequestBody TransactionRequestDto request) {

        Transaction transaction =
                transactionService.createTransaction(
                        request.getTransactionId(),
                        request.getSenderAccount(),
                        request.getReceiverAccount(),
                        request.getAmount()
                );

        return map(transaction);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponseDto getTransaction(
            @PathVariable String transactionId) {

        return map(
                transactionService.getTransaction(
                        transactionId));
    }

    @GetMapping
    public List<TransactionResponseDto> getAllTransactions() {

        return transactionService.getAllTransactions()
                .stream()
                .map(this::map)
                .toList();
    }

    private TransactionResponseDto map(
            Transaction transaction) {

        return TransactionResponseDto.builder()
                .transactionId(
                        transaction.getTransactionId())
                .senderAccount(
                        transaction.getSenderAccount())
                .receiverAccount(
                        transaction.getReceiverAccount())
                .amount(
                        transaction.getAmount())
                .status(
                        transaction.getStatus())
                .build();
    }
    @PostMapping("/transfer")
    public String transfer(
            @RequestBody TransferRequestDto request) {

        return transactionService.transferMoney(
                request.getSenderAccount(),
                request.getReceiverAccount(),
                request.getAmount());
    }

    @GetMapping("/test-load-balancer")
    public String testLoadBalancer() {
        return accountClient.getInstance();
    }
}