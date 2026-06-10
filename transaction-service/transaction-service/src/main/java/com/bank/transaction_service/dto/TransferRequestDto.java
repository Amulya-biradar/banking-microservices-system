package com.bank.transaction_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequestDto {

    private String senderAccount;

    private String receiverAccount;

    private BigDecimal amount;
}