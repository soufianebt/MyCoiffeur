package com.mycoiffeur.controllers;


import com.mycoiffeur.modele.*;
import com.mycoiffeur.repository.CoiffureRepo;
import com.mycoiffeur.repository.ProfileRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;

import java.util.Optional;

@RestController
public class ProfileController {

    Logger logger = LoggerFactory.getLogger(ProfileController.class);
    @Autowired
    CoiffureRepo coiffureRepo;
    @Autowired
    ProfileRepo profileRepo;



    @PostMapping(value = "/Profile")
    public ResponseEntity<String> createProfile(@RequestBody OperationIdentifier userId) {

        try {


            if (userId.getUserId().equals(null) || userId.getUserId().equals("")) {
                return new ResponseEntity<>("You must Specified UserId", HttpStatus.NO_CONTENT);
            }
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findById(userId.getUserId()).orElse(null));
            Optional<Profile> profile = Optional.ofNullable(profileRepo.findById(userId.getUserId())).orElse(null);
                if(profile.isPresent()){
                    logger.info("Profile already exist");
                    return new ResponseEntity<>("Profile already exist", HttpStatus.ALREADY_REPORTED);
                }
                if (coiffure.isPresent()) {
                    logger.info("Profile created successfully");
                    profileRepo.save(new Profile(userId.getUserId(), new ArrayList<Post>()));
                    return new ResponseEntity<>(" Successfully", HttpStatus.OK);
                }
                logger.warn("Coiffure Not found");
                return new ResponseEntity<>("Coiffure Not found", HttpStatus.NOT_FOUND);

            } catch(Exception e){
                logger.error(e.toString());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @GetMapping(value = "/Profile")
    public ResponseEntity<Profile> getProfile(@RequestBody OperationIdentifier userId) {
        try {
            if (userId.getUserId().equals("")) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findById(userId.getUserId()).orElse(null));
            Optional<Profile> profile = Optional.ofNullable(profileRepo.findById(userId.getUserId())).orElse(null);
            if (coiffure.isPresent()) {
                logger.info("Coiffure Found");
                if(profile.isPresent()){
                    logger.info("Profile found");
                    return new ResponseEntity<>(profile.get(), HttpStatus.OK);
                }
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            logger.warn("Coiffure Not found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
