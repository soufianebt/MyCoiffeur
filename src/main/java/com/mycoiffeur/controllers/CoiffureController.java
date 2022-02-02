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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping(value ="/coiffure/{coiffeurId}")
    public ResponseEntity<Coiffure> getCoiffure(@PathVariable String coiffeurId){
        try {
            Optional<Coiffure> coiffure = coiffureService.getCoiffeureById(coiffeurId);
            if(coiffure.isPresent()){
                logger.info("return a coiffures");
                Coiffure coiffure1 = coiffure.get();
                coiffure1.setPassWord("");
                return new ResponseEntity(coiffure1, HttpStatus.OK);
            }else{
                logger.info("return a recommendation");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value ="/coiffure")
    public ResponseEntity<String> putCoiffure(@RequestBody Coiffure coiffure){
        try {
            if(coiffure.getUserId().equals("") || coiffure.getUserId().equals(null)){

                return new ResponseEntity("Request param CoiffeurId is Empty", HttpStatus.EXPECTATION_FAILED);
            }else{
                logger.info("Coiffeur edited");
                this.coiffureService.editeCoiffeur(coiffure);
                return new ResponseEntity("Coiffeur edited", HttpStatus.OK);
            }
        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
