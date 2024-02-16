package org.example.hostfullyassignment.controllers;


import lombok.RequiredArgsConstructor;
import org.example.hostfullyassignment.dtos.PropertyDto;
import org.example.hostfullyassignment.dtos.PropertyPayloadDto;
import org.example.hostfullyassignment.exceptions.ResourceNotFoundException;
import org.example.hostfullyassignment.services.PropertyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyDto> createProperty(@RequestBody PropertyPayloadDto payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.createProperty(payload));
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<PropertyDto> updateProperty(@PathVariable UUID propertyId,
                                                      @RequestBody PropertyPayloadDto payload) {
        try {
            return ResponseEntity.ok(propertyService.updateProperty(propertyId, payload));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage())).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<PropertyDto>> getProperties(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(propertyService.getProperties(pageable));
    }
}