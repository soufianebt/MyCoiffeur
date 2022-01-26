package com.mycoiffeur.controllers;


import com.mycoiffeur.modele.*;
import com.mycoiffeur.repository.CoiffureRepo;
import com.mycoiffeur.repository.PostRepo;
import com.mycoiffeur.repository.ProfileRepo;
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
public class PostController {
    Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    CoiffureRepo coiffureRepo;
    @Autowired
    PostRepo postRepo;
    @Autowired
    ProfileRepo profileRepo;
/**
 * This Class trait how Use post a post  and add to specifical user*/
    @PostMapping(value = "/Post")
    public ResponseEntity<String> CreatePost(@RequestBody Post post) {
        try {
            /**
             * This is a check if coiffure have a profile */
            if(post.getProfileId().equals(null)){
                logger.warn("Unspecified profileId" );
                return new ResponseEntity<String>("Unspecified profileId", HttpStatus.EXPECTATION_FAILED);
            }
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findById(post.getProfileId())).orElse(null);
            Optional<Profile> profile = Optional.ofNullable(profileRepo.findById(post.getProfileId())).orElse(null);

//            if(post.getPostType() != PostType.TEXT  || post.getPostType() != PostType.IMAGE){
//                logger.warn("Unspecified Type IMAGE, TEXT" );
//                return new ResponseEntity<String>("Unspecified Type IMAGE, TEXT", HttpStatus.EXPECTATION_FAILED);
//            }
            if(coiffure.isPresent()){
                if(profile.isPresent()){
                    post.setPostId(generatePostId());
                    postRepo.save(post);
                    logger.info("The post inserted successfully");
                    return new ResponseEntity<String>("The post inserted successfully", HttpStatus.OK);
                }else{
                    logger.warn("The Profile not found");
                    return new ResponseEntity<String>("The Profile not found", HttpStatus.NOT_FOUND);
                }
            }else{
                logger.warn("The coiffure not found");
                return new ResponseEntity<String>("The coiffure not found", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

@GetMapping(value="/Post/{profileId}")
public ResponseEntity<Iterable<Post>> GetPost(@PathVariable String profileId) {
    try {
        Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findById(profileId)).orElse(null);
        if(coiffure.isPresent()){
            Iterable<Post> posts  = postRepo.findAllByProfileId(profileId);
            logger.info("The post inserted successfully");
            return new ResponseEntity<Iterable<Post>>(posts, HttpStatus.OK);
        }else{
            logger.warn("The coiffure not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch(Exception e){
        logger.error(e.toString());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    public String generatePostId(){
        return  RandomStringUtils.random(10, 0, 0, true, true, null, new SecureRandom());
    }
}
