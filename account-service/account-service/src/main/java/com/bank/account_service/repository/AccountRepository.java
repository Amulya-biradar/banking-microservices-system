package com.bank.account_service.repository;

import com.bank.account_service.entity.Account;

import java.util.List;

public interface AccountRepository {

    void save(Account account);

    Account findByAccountNumber(
            String accountNumber);

    List<Account> findAll();

    void update(Account account);

    void delete(Account account);
}