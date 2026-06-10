package com.bank.account_service.service.impl;

import com.bank.account_service.entity.Account;
import com.bank.account_service.exception.AccountNotFoundException;
import com.bank.account_service.repository.AccountRepository;
import com.bank.account_service.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl
        implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(
            AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(transactionManager = "transactionManager")
    public Account createAccount(
            String accountNumber,
            String accountHolderName,
            BigDecimal balance) {

        Account account =
                Account.builder()
                        .accountNumber(accountNumber)
                        .accountHolderName(accountHolderName)
                        .balance(balance)
                        .build();

        accountRepository.save(account);

        return account;
    }
    @Override
    @Transactional
    public void debitBalance(
            String accountNumber,
            BigDecimal amount) {

        Account account =
                accountRepository.findByAccountNumber(
                        accountNumber);

        if(account == null) {
            throw new AccountNotFoundException(
                    "Account not found");
        }

        if(account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException(
                    "Insufficient balance");
        }

        account.setBalance(
                account.getBalance().subtract(amount));

        accountRepository.update(account);
    }

    @Override
    @Transactional
    public void creditBalance(
            String accountNumber,
            BigDecimal amount) {

        Account account =
                accountRepository.findByAccountNumber(
                        accountNumber);

        if(account == null) {
            throw new AccountNotFoundException(
                    "Account not found");
        }

        account.setBalance(
                account.getBalance().add(amount));

        accountRepository.update(account);
    }
    @Override
    @Transactional(transactionManager = "transactionManager",readOnly = true)
    public Account getAccount(
            String accountNumber) {

        Account account =
                accountRepository.findByAccountNumber(
                        accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(
                    "Account not found : "
                            + accountNumber);
        }

        return account;
    }

    @Override
    @Transactional(transactionManager = "transactionManager",readOnly = true)
    public List<Account> getAllAccounts() {

        return accountRepository.findAll();
    }

    @Override
    @Transactional(transactionManager = "transactionManager")
    public void deleteAccount(
            String accountNumber) {

        Account account =
                accountRepository.findByAccountNumber(
                        accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(
                    "Account not found : "
                            + accountNumber);
        }

        accountRepository.delete(account);
    }
}