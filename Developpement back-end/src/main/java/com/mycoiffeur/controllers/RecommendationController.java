package com.mycoiffeur.controllers;


import com.mycoiffeur.recommendation.ScriptRun;
import com.mycoiffeur.services.RecommendationService;
import lombok.AllArgsConstructor;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
@AllArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final Logger logger = LoggerFactory.getLogger(RecommendationController.class);
    @GetMapping(value ="/recommendation/{profileId}")
    public ResponseEntity<Document> getRecommendation(@PathVariable String profileId){
        try {
            Document document = recommendationService.getRecommendation(profileId);
            return new ResponseEntity(document, HttpStatus.OK);
        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value ="/recommendation")
    public ResponseEntity<String> generateRecommendation(){
        try {
            ScriptRun.runScript();
            return new ResponseEntity("SUCCESS", HttpStatus.OK);
        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
