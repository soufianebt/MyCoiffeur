package com.mycoiffeur.repository;

import com.mycoiffeur.modele.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
Optional<User> findUserByUserId(String userId);
}
