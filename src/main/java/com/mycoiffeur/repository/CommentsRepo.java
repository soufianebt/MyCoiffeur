package com.mycoiffeur.repository;

import com.mycoiffeur.modele.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentsRepo extends MongoRepository<Comments, String> {

    Iterable<Comments> findAllByPostId(String strings);
}
