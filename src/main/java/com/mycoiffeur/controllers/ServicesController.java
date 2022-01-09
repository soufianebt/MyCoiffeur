package com.mycoiffeur.controllers;

import com.mycoiffeur.modele.*;
import com.mycoiffeur.repository.CoiffureRepo;
import com.mycoiffeur.repository.ServicesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ServicesController {
    //TODO : Make Service Controller
//    @Autowired
//    CoiffureRepo coiffureRepo ;
//    @Autowired
//    ServicesRepo servicesRepo;
//
//    Logger logger =  LoggerFactory.getLogger(ServicesController.class);
//
//    @GetMapping(value = "/Services/coiffureId")
//    public ResponseEntity<Iterable<Services>> getCoiffureServices(@RequestParam String coiffureId){
//
//        try {
//            Iterable<Services> services = servicesRepo.findAllById(coiffureId);
//            if (services.iterator().hasNext()) {
//                logger.info("Found List of Services");
//                return new ResponseEntity<>(services, HttpStatus.OK);
//            }else {
//                logger.info("coiffure found");
//                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            logger.error(e.toString());
//            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping(value = "/Services")
//    public ResponseEntity<String> addCoiffureServices(@RequestBody Services services){
//
//        try {
//            Optional<Coiffure> coiffure= Optional.ofNullable(coiffureRepo.findById(services.getCoiffureId())).orElse(null);
//
//            if (coiffure.isPresent()) {
//                logger.info("Service added");
//                servicesRepo.save(services);
//                return new ResponseEntity<>("Service added Successfully", HttpStatus.OK);
//            }else {
//                logger.info("coiffure not exist");
//                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            logger.error(e.toString());
//            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}

