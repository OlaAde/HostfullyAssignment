package org.example.hostfullyassignment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private User propertyManager;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    private List<Block> blocks = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    private List<Booking> bookings = new ArrayList<>();
}
