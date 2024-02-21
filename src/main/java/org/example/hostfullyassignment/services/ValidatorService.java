package org.example.hostfullyassignment.services;

import lombok.RequiredArgsConstructor;
import org.example.hostfullyassignment.entities.*;
import org.example.hostfullyassignment.exceptions.InvalidPayloadException;
import org.example.hostfullyassignment.exceptions.OperationNotPermittedException;
import org.example.hostfullyassignment.repositories.BlockRepository;
import org.example.hostfullyassignment.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidatorService {
    private final BookingRepository bookingRepository;
    private final BlockRepository blockRepository;
    private final UserService userService;

    public void validateRangeIsFree(Property property, LocalDate startDate, LocalDate endDate) throws InvalidPayloadException {
        if (startDate.isAfter(endDate)) {
            throw new InvalidPayloadException("The start date is after is end date");
        }

        Long numberOfBookingsInDateRange = bookingRepository.countBookingsCollidingWithDate(
                property.getId(),
                BookingStatus.BOOKED,
                startDate,
                endDate);

        if (numberOfBookingsInDateRange != 0) {
            throw new InvalidPayloadException("There is an overlapping booking in the proposed date range");
        }

        Long numberOfBlocksInDateRange = blockRepository.countBlocksCollidingWithDate(
                property.getId(),
                startDate,
                endDate);

        if (numberOfBlocksInDateRange != 0) {
            throw new InvalidPayloadException("There is an overlapping block in the proposed date range");
        }
    }

    public void validateRangeIsFreeExcludingBooking(Property property, LocalDate startDate, LocalDate endDate, Booking bookingToExclude) throws InvalidPayloadException {
        if (startDate.isAfter(endDate)) {
            throw new InvalidPayloadException("The start date is after is end date");
        }

        List<Booking> bookingsInDateRange = bookingRepository.findBookingsCollidingWithDate(
                property.getId(),
                BookingStatus.BOOKED,
                startDate,
                endDate);

        bookingsInDateRange = bookingsInDateRange.stream().filter(booking -> booking.getId() != bookingToExclude.getId()).toList();
        if (!bookingsInDateRange.isEmpty()) {
            throw new InvalidPayloadException("There is an overlapping booking in the proposed date range");
        }

        Long numberOfBlocksInDateRange = blockRepository.countBlocksCollidingWithDate(
                property.getId(),
                startDate,
                endDate);

        if (numberOfBlocksInDateRange != 0) {
            throw new InvalidPayloadException("There is an overlapping block in the proposed date range");
        }
    }

    public void validateRangeIsFreeExcludingBlock(Property property, LocalDate startDate, LocalDate endDate, Block blockToExclude) throws InvalidPayloadException {
        if (startDate.isAfter(endDate)) {
            throw new InvalidPayloadException("The start date is after is end date");
        }

        Long numberOfBookingsInDateRange = bookingRepository.countBookingsCollidingWithDate(
                property.getId(),
                BookingStatus.BOOKED,
                startDate,
                endDate);

        if (numberOfBookingsInDateRange != 0) {
            throw new InvalidPayloadException("There is an overlapping booking in the proposed date range");
        }

        List<Block> blocksInDateRange = blockRepository.findBlocksCollidingWithDate(
                property.getId(),
                startDate,
                endDate);

        blocksInDateRange = blocksInDateRange.stream().filter(booking -> booking.getId() != blockToExclude.getId()).toList();

        if (!blocksInDateRange.isEmpty()) {
            throw new InvalidPayloadException("There is an overlapping block in the proposed date range");
        }
    }

    public void userIsPermitted(Property property) throws OperationNotPermittedException {
        User user = userService.getCurrentUser();
        boolean isOwner = property.getOwner().getId() == user.getId();
        boolean isPropertyManager = (property.getPropertyManager() != null) &&
                (property.getPropertyManager().getId() == user.getId());
        if (!isOwner && !isPropertyManager) {
            throw new OperationNotPermittedException("You are not permitted to perform this action");
        }
    }
}
