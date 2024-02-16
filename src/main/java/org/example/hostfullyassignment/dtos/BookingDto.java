package org.example.hostfullyassignment.dtos;

import org.example.hostfullyassignment.entities.BookingStatus;

import java.time.LocalDate;
import java.util.UUID;

public record BookingDto(UUID id, LocalDate startDate, LocalDate endDate, BookingStatus status, UserDto guest) {
}
