package com.mycoiffeur.controllers;


import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.modele.Post;
import com.mycoiffeur.modele.Profile;
import com.mycoiffeur.modele.Services;
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
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findById(post.getCoiffureId())).orElse(null);
            Optional<Profile> profile = Optional.ofNullable(profileRepo.findById(post.getCoiffureId())).orElse(null);
            if(coiffure.isPresent()){
                if(profile.isPresent()){
                    Profile profile1 = profile.get();
                    post.setPostId(generatePostId());
                    profile1.addPost(post);
                    profileRepo.save(profile1);
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

@GetMapping(value="/Post/{UserId}")
public ResponseEntity<Iterable<Post>> GetPost(@PathVariable String UserId) {
    try {
        Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findById(UserId)).orElse(null);
        if(coiffure.isPresent()){
            Iterable<Post> posts  = postRepo.findAllByCoiffureId(UserId);
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
