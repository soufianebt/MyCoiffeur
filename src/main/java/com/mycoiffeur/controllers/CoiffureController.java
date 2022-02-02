package com.mycoiffeur.controllers;

import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.services.CoiffureService;
import com.mycoiffeur.services.RecommendationService;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CoiffureController {
    private final CoiffureService coiffureService;
    private final Logger logger = LoggerFactory.getLogger(CoiffureController.class);
    @GetMapping(value ="/coiffure")
    public ResponseEntity<Iterable<Coiffure>> getCoiffures(){
        try {
            Iterable<Coiffure> coiffures = coiffureService.getCoiffures();
            logger.info("return a recommendation");
            return new ResponseEntity(coiffures, HttpStatus.OK);
        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
