package com.bank.account_service.controller;

import com.bank.account_service.dto.AccountRequestDto;
import com.bank.account_service.dto.AccountResponseDto;
import com.bank.account_service.dto.BalanceUpdateRequestDto;
import com.bank.account_service.entity.Account;
import com.bank.account_service.service.AccountService;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final Environment environment;

    public AccountController(
            AccountService accountService, Environment environment) {

        this.accountService = accountService;
        this.environment = environment;
    }

    @PostMapping
    public AccountResponseDto createAccount(
            @RequestBody AccountRequestDto request) {

        Account account =
                accountService.createAccount(
                        request.getAccountNumber(),
                        request.getAccountHolderName(),
                        request.getBalance()
                );

        return mapToResponse(account);
    }

    @GetMapping("/{accountNumber}")
    public AccountResponseDto getAccount(
            @PathVariable String accountNumber) {

        Account account =
                accountService.getAccount(
                        accountNumber);

        return mapToResponse(account);
    }

    @PutMapping("/credit")
    public String credit(
            @RequestBody BalanceUpdateRequestDto request) {

        accountService.creditBalance(
                request.getAccountNumber(),
                request.getAmount());

        return "Amount Credited";
    }

    @PutMapping("/debit")
    public String debit(
            @RequestBody BalanceUpdateRequestDto request) {

        accountService.debitBalance(
                request.getAccountNumber(),
                request.getAmount());

        return "Amount Debited";
    }

    @GetMapping
    public List<AccountResponseDto> getAllAccounts() {

        return accountService.getAllAccounts()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @DeleteMapping("/{accountNumber}")
    public String deleteAccount(
            @PathVariable String accountNumber) {

        accountService.deleteAccount(
                accountNumber);

        return "Account Deleted Successfully";
    }

    private AccountResponseDto mapToResponse(
            Account account) {

        return AccountResponseDto.builder()
                .accountNumber(
                        account.getAccountNumber())
                .accountHolderName(
                        account.getAccountHolderName())
                .balance(
                        account.getBalance())
                .build();
    }
    @GetMapping("/instance")
    public String getInstance() {
        return "Handled by port: " + environment.getProperty("local.server.port");
    }
}