package com.devsu.dto;

import com.devsu.utils.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ReportTransactionDTO {

    private Instant date;
    private String client;
    private Long accountNumber;
    private AccountType accountType;
    private BigDecimal initialBalance;
    private Boolean accountStatus;
    private BigDecimal amount;
    private BigDecimal availableBalance;

}
