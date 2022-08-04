package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Post;
import com.mycoiffeur.modele.Services;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepo extends MongoRepository<Post, String> {
    Iterable<Post> findAllByProfileId(String email);
}
