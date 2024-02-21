package org.example.hostfullyassignment.repositories;


import org.example.hostfullyassignment.entities.Block;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BlockRepository extends CrudRepository<Block, UUID> {

    @Query(value = "select count(*) from Block b where b.property.id=?1 and b.startDate <= ?3 AND b.endDate >= ?2")
    Long countBlocksCollidingWithDate(UUID propertyId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select b from Block b where b.property.id=?1 and b.startDate <= ?3 AND b.endDate >= ?2")
    List<Block> findBlocksCollidingWithDate(UUID propertyId, LocalDate startDate, LocalDate endDate);

}