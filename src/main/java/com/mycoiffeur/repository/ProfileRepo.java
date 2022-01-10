package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepo extends MongoRepository<Profile, String> {
}
