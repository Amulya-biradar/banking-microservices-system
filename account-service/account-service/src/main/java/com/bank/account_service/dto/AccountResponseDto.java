package com.bank.account_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AccountResponseDto {

    private String accountNumber;

    private String accountHolderName;

    private BigDecimal balance;
}