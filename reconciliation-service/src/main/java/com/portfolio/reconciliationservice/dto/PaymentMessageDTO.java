package com.portfolio.reconciliationservice.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentMessageDTO {
    private String transaction_id;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String gateway;
}