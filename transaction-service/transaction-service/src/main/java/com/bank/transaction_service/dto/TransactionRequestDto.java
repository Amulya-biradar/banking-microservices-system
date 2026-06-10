package com.bank.transaction_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {

    private String transactionId;

    private String senderAccount;

    private String receiverAccount;

    private BigDecimal amount;
}