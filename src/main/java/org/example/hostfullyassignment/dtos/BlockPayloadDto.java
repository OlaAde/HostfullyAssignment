package org.example.hostfullyassignment.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BlockPayloadDto(@NotNull(message = "Start date is required") LocalDate startDate,
                              @NotNull(message = "End date is required") LocalDate endDate) {
}
