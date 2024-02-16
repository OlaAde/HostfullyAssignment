package org.example.hostfullyassignment.dtos;


import java.util.UUID;

public record UserDto(UUID id, String name, String email, Integer age) {
}
