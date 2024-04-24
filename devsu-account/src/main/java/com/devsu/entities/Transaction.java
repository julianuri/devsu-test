package com.devsu.entities;

import com.devsu.utils.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private Instant updateDate;
    private TransactionType type;
    private BigDecimal amount;

    @JsonIgnore
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    private Account account;

    @Column(name = "account_id")
    private Long accountId;

}
