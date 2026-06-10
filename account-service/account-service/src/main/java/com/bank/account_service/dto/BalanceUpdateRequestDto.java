package com.bank.account_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceUpdateRequestDto {

    private String accountNumber;

    private BigDecimal amount;
}