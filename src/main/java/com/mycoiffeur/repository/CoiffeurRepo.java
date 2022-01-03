package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Coiffure;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoiffeurRepo extends MongoRepository<Coiffure, String> {
}
