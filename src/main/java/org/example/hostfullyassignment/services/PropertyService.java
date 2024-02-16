package org.example.hostfullyassignment.services;

import lombok.RequiredArgsConstructor;
import org.example.hostfullyassignment.dtos.PropertyDto;
import org.example.hostfullyassignment.dtos.PropertyPayloadDto;
import org.example.hostfullyassignment.dtos.UserDto;
import org.example.hostfullyassignment.entities.Property;
import org.example.hostfullyassignment.entities.User;
import org.example.hostfullyassignment.exceptions.ResourceNotFoundException;
import org.example.hostfullyassignment.repositories.PropertyRepository;
import org.example.hostfullyassignment.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public PropertyDto createProperty(PropertyPayloadDto payload) {
        Property property = modelMapper.map(payload, Property.class);
        property.setOwner(userService.getCurrentUser());
        if (payload.propertyManagerId() != null) {
            Optional<User> propertyManager = userRepository.findById(payload.propertyManagerId());
            if (propertyManager.isPresent()) {
                property.setPropertyManager(propertyManager.get());
            }
        }

        property = propertyRepository.save(property);

        return toDto(property);
    }

    public PropertyDto updateProperty(UUID id, PropertyPayloadDto payload) throws ResourceNotFoundException {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        if (payload.propertyManagerId() != null) {
            Optional<User> propertyManager = userRepository.findById(payload.propertyManagerId());
            if (propertyManager.isPresent()) {
                property.setPropertyManager(propertyManager.get());
            }
        }
        property.setName(property.getName());
        property.setAddress(property.getAddress());

        property = propertyRepository.save(property);


        return toDto(property);
    }

    private static PropertyDto toDto(Property property) {
        UserDto properManager = property.getPropertyManager() != null ? new UserDto(property.getPropertyManager().getId(),
                property.getPropertyManager().getName(), property.getPropertyManager().getEmail(), property.getPropertyManager().getAge()) : null;

        UserDto owner = new UserDto(property.getOwner().getId(), property.getOwner().getName(),
                property.getOwner().getEmail(), property.getOwner().getAge());


        return new PropertyDto(property.getId(), property.getName(), property.getAddress(), properManager, owner);
    }

    public Page<PropertyDto> getProperties(Pageable pageable) {
        return propertyRepository.findAll(pageable)
                .map(PropertyService::toDto);
    }
}
