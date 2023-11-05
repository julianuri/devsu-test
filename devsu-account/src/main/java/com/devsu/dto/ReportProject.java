package com.devsu.dto;

import com.devsu.utils.enums.AccountType;

import java.math.BigDecimal;
import java.time.Instant;

public interface ReportProject {
    Instant getUpdateDate();
    String getClient();
    Long getId();
    String getType();
    BigDecimal getInitialBalance();
    Boolean getStatus();
    BigDecimal getAmount();
    BigDecimal getCurrentBalance();
}
