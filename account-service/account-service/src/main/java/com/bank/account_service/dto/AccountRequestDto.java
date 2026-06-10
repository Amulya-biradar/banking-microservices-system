package com.bank.account_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequestDto {

    private String accountNumber;

    private String accountHolderName;

    private BigDecimal balance;
}