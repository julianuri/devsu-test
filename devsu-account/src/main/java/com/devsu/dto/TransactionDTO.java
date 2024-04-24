package com.devsu.dto;

import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.math.BigDecimal;

public record TransactionDTO(@NonNull BigDecimal amount,
                             @Min(1) @NonNull Long accountId,
                             Long id) {
}
