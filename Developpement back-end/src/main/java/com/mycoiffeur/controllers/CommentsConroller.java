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
public class CommentsConroller {
    Logger logger = LoggerFactory.getLogger(CommentsConroller.class);
    @Autowired
    ClientRepo clientRepo;
    @Autowired
    PostRepo postRepo;
    @Autowired
    ProfileRepo profileRepo;
    @Autowired
    CommentsRepo commentsRepo;
    @PostMapping(value = "/Comment")
    public ResponseEntity<String> CreateComment(@RequestBody Comments comments) {
        try {
            /**
             * this a secure check if the client can post a comment  */
            Optional<Client> client = Optional.ofNullable(clientRepo.findById(comments.getUserId())).orElse(null);
            Optional<Post> post = Optional.ofNullable(postRepo.findById(comments.getPostId())).orElse(null);
            if(client.isPresent()){
                if(post.isPresent()){
                    comments.setCommentId(generateCommentId());
                    commentsRepo.save(comments);
                    logger.info("The comment inserted successfully");
                    return new ResponseEntity<String>("The post comment successfully", HttpStatus.OK);
                }else{
                    logger.warn("The post not found");
                    return new ResponseEntity<String>("The post not found", HttpStatus.NOT_FOUND);
                }

            }else{
                logger.warn("The client not found");
                return new ResponseEntity<String>("The client not found", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/Comment/{postId}")
    public ResponseEntity<Iterable<Comments>> GetAllComment(@PathVariable String postId){
        try {
           Iterable<Comments> comments =  commentsRepo.findAllByPostId(postId);
            logger.info("Comments found");
            return new ResponseEntity(comments, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public String generateCommentId(){
        return  RandomStringUtils.random(10, 0, 0, true, true, null, new SecureRandom());
    }

}
