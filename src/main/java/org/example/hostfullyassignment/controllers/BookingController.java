package org.example.hostfullyassignment.controllers;


import lombok.RequiredArgsConstructor;
import org.example.hostfullyassignment.dtos.BookingDto;
import org.example.hostfullyassignment.dtos.BookingPayloadDto;
import org.example.hostfullyassignment.exceptions.InvalidPayloadException;
import org.example.hostfullyassignment.exceptions.ResourceNotFoundException;
import org.example.hostfullyassignment.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/properties/{propertyId}/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@PathVariable UUID propertyId, @RequestBody BookingPayloadDto payload) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(propertyId, payload));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage())).build();
        } catch (InvalidPayloadException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage())).build();
        }
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable UUID propertyId, @PathVariable UUID bookingId,
                                                    @RequestBody BookingPayloadDto payload) {
        try {
            return ResponseEntity.ok(bookingService.updateBooking(propertyId, bookingId, payload));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage())).build();
        } catch (InvalidPayloadException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage())).build();
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable UUID bookingId) {
        try {
            return ResponseEntity.ok(bookingService.getBooking(bookingId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage())).build();
        }
    }

    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingDto> cancelBooking(@PathVariable UUID bookingId) {
        try {
            return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage())).build();
        }
    }

    @PatchMapping("/{bookingId}/rebook")
    public ResponseEntity<BookingDto> rebookBooking(@PathVariable UUID bookingId) {
        try {
            return ResponseEntity.ok(bookingService.rebookBooking(bookingId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage())).build();
        }
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable UUID bookingId) {
        bookingService.deleteBooking(bookingId);
    }
}
