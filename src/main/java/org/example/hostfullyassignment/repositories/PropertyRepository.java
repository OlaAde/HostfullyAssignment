package org.example.hostfullyassignment.repositories;


import org.example.hostfullyassignment.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

}
