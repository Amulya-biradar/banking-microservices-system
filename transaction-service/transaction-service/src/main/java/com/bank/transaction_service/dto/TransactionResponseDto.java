package com.bank.transaction_service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponseDto {

    private String transactionId;

    private String senderAccount;

    private String receiverAccount;

    private BigDecimal amount;

    private String status;
}