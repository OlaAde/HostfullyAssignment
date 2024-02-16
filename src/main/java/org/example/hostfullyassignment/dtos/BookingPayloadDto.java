package org.example.hostfullyassignment.dtos;

import jakarta.validation.constraints.NotNull;
import org.example.hostfullyassignment.entities.BookingStatus;

import java.time.LocalDate;
import java.util.UUID;

public record BookingPayloadDto(@NotNull(message = "Start date is required") LocalDate startDate,
                                @NotNull(message = "End date is required") LocalDate endDate,
                                BookingStatus status,
                                @NotNull(message = "Guest id is required") UUID guestId) {
}
