package com.bank.account_service.service;

import com.bank.account_service.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account createAccount(
            String accountNumber,
            String accountHolderName,
            BigDecimal balance);

    Account getAccount(
            String accountNumber);

    List<Account> getAllAccounts();

    void deleteAccount(
            String accountNumber);

    void debitBalance(
            String accountNumber,
            BigDecimal amount);

    void creditBalance(
            String accountNumber,
            BigDecimal amount);
}