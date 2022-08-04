package com.mycoiffeur.services;

import com.mycoiffeur.repository.RecommendationRepo;
import lombok.AllArgsConstructor;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class RecommendationServiceImp implements RecommendationService{
    RecommendationRepo recommendationRepo;
    MongoTemplate mongoTemplate;
    @Override
    public JsonObject listAllRecommendation() {
        //TODO: Implimented
        return null;
    }

    @Override
    public Document getRecommendation(String profileId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(profileId));
        var document =  mongoTemplate.findOne(query, Document.class, "Recommendation");
        if(document == null || document.size() == 0){
           HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("message", "The Coiffure Doesn't have any Review");
            return new Document(map);
        }
        return document;
    }
}
