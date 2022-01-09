package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Services;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ServicesRepo extends MongoRepository<Services, String> {

    @Override
    Iterable<Services> findAllById(Iterable<String> strings);


}
