package org.example.hostfullyassignment.dtos;

import java.util.UUID;

public record PropertyDto(
        UUID id,
        String name,
        String address,
        UserDto propertyManager,
        UserDto owner) {
}