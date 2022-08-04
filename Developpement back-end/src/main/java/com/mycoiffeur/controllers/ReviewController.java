package com.mycoiffeur.controllers;

import com.mycoiffeur.modele.*;
import com.mycoiffeur.repository.*;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Optional;

@RestController
public class ReviewController {
    Logger logger = LoggerFactory.getLogger(Review.class);
    @Autowired
    ClientRepo clientRepo;
    @Autowired
    ProfileRepo profileRepo;
    @Autowired
    ReviewRepo reviewRepo;
    @PostMapping(value = "/Review" )
    public ResponseEntity<String> createReview(@RequestBody Review review) {
        try {
            Optional<Client> client = Optional.ofNullable(clientRepo.findById(review.getClientId())).orElse(null);
            Optional<Profile> profile = Optional.ofNullable(profileRepo.findById(review.getProfileId())).orElse(null);
            if(client.isEmpty()){
                logger.info("Client not exist");
                return new ResponseEntity<>("Client not exist", HttpStatus.ALREADY_REPORTED);
            }
            if (profile.isPresent()) {
                logger.info("Review Inserted successfully");
                review.setReviewId(generateReviewId());
                reviewRepo.save(review);
                return new ResponseEntity<>(" Successfully", HttpStatus.OK);
            }
            logger.warn("Profile Not found");
            return new ResponseEntity<>("Profile Not found", HttpStatus.NOT_FOUND);

        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/Review/{profileId}" )
    public ResponseEntity<Iterable<Review>> createReview(@PathVariable String profileId) {
        try {
            Optional<Profile> profile = Optional.ofNullable(profileRepo.findById(profileId)).orElse(null);
            if(profile.isEmpty()){
                logger.info("profile not exist");
                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
            }
            Iterable<Review> reviews = reviewRepo.findAllByProfileId(profileId);
            logger.warn("Profile Not found");
            return new ResponseEntity<>( reviews, HttpStatus.OK);

        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public String generateReviewId(){
        return  RandomStringUtils.random(10, 0, 0, true, true, null, new SecureRandom());
    }

}
