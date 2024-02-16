package org.example.hostfullyassignment.repositories;


import org.example.hostfullyassignment.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

}
