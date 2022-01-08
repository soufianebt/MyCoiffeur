package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Coiffure;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CoiffeurRepo extends MongoRepository<Coiffure, String> {
    Optional<Coiffure> findByEmail(String email);
}
