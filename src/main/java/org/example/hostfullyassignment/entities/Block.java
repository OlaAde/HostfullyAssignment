package org.example.hostfullyassignment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "blocks")
@Setter
@Getter
public class Block {
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull(message = "Start date is required")
    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;
}
