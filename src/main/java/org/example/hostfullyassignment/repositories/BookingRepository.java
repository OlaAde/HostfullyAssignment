package org.example.hostfullyassignment.repositories;


import org.example.hostfullyassignment.entities.Booking;
import org.example.hostfullyassignment.entities.BookingStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, UUID> {

    @Query(value = "select count (*) from Booking b where b.property.id=?1 and b.status = ?2 and b.startDate <= ?4 AND b.endDate >= ?3")
    Long countBookingsCollidingWithDate(UUID propertyId, BookingStatus status, LocalDate startDate, LocalDate endDate);

    @Query(value = "select b from Booking b where b.property.id=?1 and b.status = ?2 and b.startDate <= ?4 AND b.endDate >= ?3")
    List<Booking> findBookingsCollidingWithDate(UUID propertyId, BookingStatus status, LocalDate startDate, LocalDate endDate);
}
