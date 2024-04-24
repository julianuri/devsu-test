package com.devsu.dto;

import com.devsu.utils.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ReportDTO implements ReportProject {

    private Instant updateDate;
    private String client;
    private Long id;
    @JsonIgnore
    private String type;
    private AccountType accountType;
    private BigDecimal initialBalance;
    private Boolean status;
    private BigDecimal amount;
    private BigDecimal currentBalance;

}
