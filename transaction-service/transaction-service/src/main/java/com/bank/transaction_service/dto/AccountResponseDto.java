package com.bank.transaction_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountResponseDto {

    private String accountNumber;

    private String accountHolderName;

    private BigDecimal balance;
}