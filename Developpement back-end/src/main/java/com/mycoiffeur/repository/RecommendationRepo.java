package com.mycoiffeur.repository;

import org.bson.json.JsonObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationRepo extends MongoRepository<JsonObject, String> {
}
