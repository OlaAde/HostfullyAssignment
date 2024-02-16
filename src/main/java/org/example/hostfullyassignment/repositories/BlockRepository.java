package org.example.hostfullyassignment.repositories;


import org.example.hostfullyassignment.entities.Block;
import org.example.hostfullyassignment.entities.Property;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BlockRepository extends CrudRepository<Block, UUID> {
    Long countAllByPropertyAndStartDateBetweenOrEndDateIsBetween(Property property, LocalDate startDateUpperLimit, LocalDate startDateLowerLimit, LocalDate endDateUpperLimit, LocalDate endDateLowerLimit);

    List<Block> getAllByPropertyAndStartDateBetweenOrEndDateIsBetween(Property property, LocalDate startDateUpperLimit, LocalDate startDateLowerLimit, LocalDate endDateUpperLimit, LocalDate endDateLowerLimit);

}
