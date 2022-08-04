package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepo extends MongoRepository<Review, String> {

    Iterable<Review> findAllByProfileId(String profileId);
}
