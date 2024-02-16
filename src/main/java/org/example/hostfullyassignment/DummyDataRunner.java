package org.example.hostfullyassignment;

import lombok.RequiredArgsConstructor;
import org.example.hostfullyassignment.entities.Property;
import org.example.hostfullyassignment.entities.User;
import org.example.hostfullyassignment.repositories.PropertyRepository;
import org.example.hostfullyassignment.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DummyDataRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;


    //Adds two dummy users and some properties
    @Override
    public void run(String... args) {
        User dummyOwner = new User(UUID.fromString("9013181e-d32d-47f1-aaea-b2e77f744e8f"), "Emmanuel", "iretimi.emmanuel@gmail.com", 27);
        dummyOwner = userRepository.save(dummyOwner);

        userRepository.save(new User(UUID.fromString("5b93eb1d-7987-4325-b83e-096217f8dbc8"), "Oladipo", "adeogo.oladipo@gmail.com", 35));

        propertyRepository.save(new Property(UUID.fromString("159d59d0-da98-4a77-b2d0-e3152f51323c"), "Ahmed Apartment", "Antalya, Turkey", null, dummyOwner, new ArrayList<>(), new ArrayList<>()));
        propertyRepository.save(new Property(UUID.fromString("6f165352-4842-490e-9400-a645957183aa"), "Katya Apartment", "Antalya, Turkey", null, dummyOwner, new ArrayList<>(), new ArrayList<>()));
    }
}
