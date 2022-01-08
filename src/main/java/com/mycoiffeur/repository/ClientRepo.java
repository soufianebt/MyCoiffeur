package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Client;
import com.mycoiffeur.modele.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface  ClientRepo extends MongoRepository<Client, String> {

    Optional<User> findUserByEmail(String email);
    Optional<Client> findByEmail(String email);
}
