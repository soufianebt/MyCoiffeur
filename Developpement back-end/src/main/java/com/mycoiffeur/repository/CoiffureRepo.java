package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Coiffure;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CoiffureRepo extends MongoRepository<Coiffure, String> {

    Iterable<Coiffure> findAllByEmail(Iterable<String> iterable);

    Optional<Coiffure> findByEmail(String email);
}
