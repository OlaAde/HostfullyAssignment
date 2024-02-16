package org.example.hostfullyassignment.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PropertyPayloadDto(@NotNull(message = "Name is required") String name,
                                 @NotNull(message = "Address is required") String address,
                                 UUID propertyManagerId) {
}
