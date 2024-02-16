package org.example.hostfullyassignment.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record BlockDto(UUID id, LocalDate startDate, LocalDate endDate) {
}
