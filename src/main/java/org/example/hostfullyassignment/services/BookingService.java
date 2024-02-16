package org.example.hostfullyassignment.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.hostfullyassignment.dtos.BookingDto;
import org.example.hostfullyassignment.dtos.BookingPayloadDto;
import org.example.hostfullyassignment.dtos.UserDto;
import org.example.hostfullyassignment.entities.Booking;
import org.example.hostfullyassignment.entities.BookingStatus;
import org.example.hostfullyassignment.entities.Property;
import org.example.hostfullyassignment.entities.User;
import org.example.hostfullyassignment.exceptions.InvalidPayloadException;
import org.example.hostfullyassignment.exceptions.ResourceNotFoundException;
import org.example.hostfullyassignment.repositories.BookingRepository;
import org.example.hostfullyassignment.repositories.PropertyRepository;
import org.example.hostfullyassignment.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final PropertyRepository propertyRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ValidatorService validatorService;
    private final ModelMapper modelMapper;

    @Transactional
    public BookingDto createBooking(UUID propertyId, BookingPayloadDto payload) throws ResourceNotFoundException, InvalidPayloadException {
        Property property = propertyRepository.findById(propertyId).orElseThrow(ResourceNotFoundException::new);
        validatorService.validateRangeIsFree(property, payload.startDate(), payload.endDate());

        Booking booking = modelMapper.map(payload, Booking.class);
        booking.setProperty(property);
        booking.setStatus(BookingStatus.BOOKED);

        User guest = userRepository.findById(payload.guestId()).orElseThrow(() -> new ResourceNotFoundException("Guest user not found"));
        booking.setGuest(guest);

        booking = bookingRepository.save(booking);

        return toDto(booking);
    }

    @Transactional
    public BookingDto updateBooking(UUID propertyId, UUID bookingId, BookingPayloadDto payload) throws ResourceNotFoundException, InvalidPayloadException {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        validatorService.validateRangeIsFreeExcludingBooking(property, payload.startDate(), payload.endDate(), booking);

        booking.setStartDate(payload.startDate());
        booking.setEndDate(payload.endDate());
        User guest = userRepository.findById(payload.guestId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        booking.setGuest(guest);

        booking = bookingRepository.save(booking);

        return toDto(booking);
    }

    public void deleteBooking(UUID bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public BookingDto cancelBooking(UUID bookingId) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);

        booking = bookingRepository.save(booking);
        return toDto(booking);
    }

    public BookingDto rebookBooking(UUID bookingId) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.BOOKED);

        booking = bookingRepository.save(booking);
        return toDto(booking);
    }

    public BookingDto getBooking(UUID bookingId) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return toDto(booking);
    }

    private BookingDto toDto(Booking booking){
        UserDto guest = booking.getGuest() != null ? new UserDto(booking.getGuest().getId(), booking.getGuest().getName(),
                booking.getGuest().getEmail(), booking.getGuest().getAge()): null;
        return new BookingDto(booking.getId(), booking.getStartDate(), booking.getEndDate(), booking.getStatus(), guest);
    }
}