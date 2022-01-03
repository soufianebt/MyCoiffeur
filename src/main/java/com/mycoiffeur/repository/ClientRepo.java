package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  ClientRepo extends MongoRepository<Client, String> {

}
