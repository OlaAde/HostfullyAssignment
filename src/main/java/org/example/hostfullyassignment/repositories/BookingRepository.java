package org.example.hostfullyassignment.repositories;


import org.example.hostfullyassignment.entities.Booking;
import org.example.hostfullyassignment.entities.BookingStatus;
import org.example.hostfullyassignment.entities.Property;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, UUID> {
    Long countAllByPropertyAndStatusAndStartDateBetweenOrEndDateIsBetween(Property property, BookingStatus status, LocalDate startDateUpperLimit, LocalDate startDateLowerLimit, LocalDate endDateUpperLimit, LocalDate endDateLowerLimit);
    List<Booking> getAllByPropertyAndStatusAndStartDateBetweenOrEndDateIsBetween(Property property, BookingStatus status, LocalDate startDateUpperLimit, LocalDate startDateLowerLimit, LocalDate endDateUpperLimit, LocalDate endDateLowerLimit);
}
