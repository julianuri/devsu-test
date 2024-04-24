package com.devsu.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

public record ClientDTO(@Size(max = 150, min = 6) @NonNull String name,
                        @Size(max = 10, min = 1) @NonNull String gender,
                        @Min(18) @Max(130) @NonNull Integer age,
                        @Size(max = 50,min = 5) @NonNull String identification,
                        @Size(max = 150, min = 5) @NonNull String address,
                        @Size(max = 14, min = 7) @NonNull String phone,
                        @Size(max = 20, min = 8) @NonNull String password,
                        Boolean status, Long id) {
}
