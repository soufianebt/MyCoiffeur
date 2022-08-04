package com.mycoiffeur.services;

import org.bson.Document;
import org.bson.json.JsonObject;

public interface RecommendationService {
    public JsonObject listAllRecommendation();
    public Document getRecommendation(String profileId);
}
