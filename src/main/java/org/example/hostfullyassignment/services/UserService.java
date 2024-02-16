package org.example.hostfullyassignment.services;

import lombok.RequiredArgsConstructor;
import org.example.hostfullyassignment.entities.User;
import org.example.hostfullyassignment.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // TODO: Returns a user from the list of users in the database. Once the user management is implemented, this will be changed.
    User getCurrentUser() {
        return userRepository.findAll().iterator().next();
    }
}
