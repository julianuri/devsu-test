package com.devsu.dto;

import com.devsu.utils.enums.AccountType;
import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.math.BigDecimal;

public record AccountDTO(@NonNull AccountType type,
                         @Min(0) BigDecimal initialBalance,
                         @Min(1) Long personId,
                         Boolean status,
                         Long id) {
}
