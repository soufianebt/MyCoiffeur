package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.modele.Services;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ServicesRepo extends MongoRepository<Services, String> {

    Iterable<Services> findAllByCoiffureId(String email);
}
