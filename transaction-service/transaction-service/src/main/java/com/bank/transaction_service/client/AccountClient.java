package com.bank.transaction_service.client;

import com.bank.transaction_service.dto.AccountResponseDto;
import com.bank.transaction_service.dto.BalanceUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/accounts/{accountNumber}")
    AccountResponseDto getAccount(
            @PathVariable String accountNumber);

    @PutMapping("/accounts/debit")
    String debit(
            @RequestBody BalanceUpdateRequestDto request);

    @PutMapping("/accounts/credit")
    String credit(
            @RequestBody BalanceUpdateRequestDto request);

    @GetMapping("/accounts/instance")
    String getInstance();
}