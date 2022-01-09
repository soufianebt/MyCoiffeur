package com.mycoiffeur.controllers;

import com.mycoiffeur.modele.Client;
import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.modele.Identifier;
import com.mycoiffeur.modele.User;
import com.mycoiffeur.repository.ClientRepo;
import com.mycoiffeur.repository.CoiffureRepo;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.SecureRandom;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private CoiffureRepo coiffureRepo;
    @Autowired
    private ClientRepo clientRepo;

        Logger logger =  LoggerFactory.getLogger(UserController.class);


    @RequestMapping("/")
    public String firstPage() {
        return "Hello";
    }

    @PostMapping(value = "/SignUp")
    public ResponseEntity<String> singUp(@RequestBody User user){
        try{
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findByEmail(user.getEmail()).orElse(null));
            Optional<Client> client = Optional.ofNullable(clientRepo.findByEmail(user.getEmail()).orElse(null));

            if(coiffure.isPresent() || client.isPresent()){
                        return new ResponseEntity<>("user already exist",HttpStatus.ALREADY_REPORTED);
            }
            if(user.getUserType().equals("Client")){

                clientRepo.save(new Client(generateUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassWord(), user.getAddress(),user.getUserType(),0f, 0f));
            }else if(user.getUserType().equals("Coiffure")){
                coiffureRepo.save(new Coiffure(generateUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassWord(), user.getAddress(),user.getUserType(),false, false));
            }else{
                logger.info("Please specify the type");
                return new ResponseEntity<>("Please specify the type",HttpStatus.EXPECTATION_FAILED);
            }
            logger.info("Created Successfully");
        return new ResponseEntity<>("Created Successfully",HttpStatus.OK);
    }catch (Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/SignUp/{email}")
    public ResponseEntity<User> singUp(@PathVariable String email) {
        try {
            Optional<Client> client = Optional.ofNullable(clientRepo.findByEmail(email).orElse(null));
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findByEmail(email).orElse(null));
            if (client.isPresent()) {
                logger.info("find User client");
                Client modifiedClient = client.get();
                modifiedClient.setPassWord("");
                return new ResponseEntity<>(modifiedClient, HttpStatus.OK);
            }else if(coiffure.isPresent()){
                logger.info("find User Coiffure");
                Coiffure modifiedCoiffure = coiffure.get();
                modifiedCoiffure.setPassWord("");
                return new ResponseEntity<>(modifiedCoiffure, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/SignIn")
    public ResponseEntity<User> signIn(@RequestBody Identifier identifier){

        try {
            Optional<Client> client = Optional.ofNullable(clientRepo.findByEmail(identifier.getEmail()).orElse(null));
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findByEmail(identifier.getEmail()).orElse(null));
            if (client.isPresent()) {
                if(identifier.getPassWord().equals(client.get().getPassWord())){
                    logger.info("Client found");
                    Client modifiedClient = client.get();
                    modifiedClient.setPassWord("");
                    return new ResponseEntity<>(modifiedClient, HttpStatus.OK);
                }else{
                    logger.warn("Client Unauthorized");
                    return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
                }
            }else if(coiffure.isPresent()){
                if(identifier.getPassWord().equals(coiffure.get().getPassWord())) {
                    logger.info("coiffure found");
                    Coiffure modifiedCoiffure = coiffure.get();
                    modifiedCoiffure.setPassWord("");
                    return new ResponseEntity<>(modifiedCoiffure, HttpStatus.OK);
                }else{
                    logger.warn("Client Unauthorized");
                    return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
                }
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/UserUpdate")
    public ResponseEntity<String> UpdateUser(@RequestBody User user) {

        try {
            if(user.getUserId() == null){
                return new ResponseEntity<>("You must Specified UserId",HttpStatus.NO_CONTENT);
            }

            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findById(user.getUserId()).orElse(null));
            Optional<Client> client = Optional.ofNullable(clientRepo.findById(user.getUserId()).orElse(null));
            if (coiffure.isPresent() || client.isPresent()) {
                if(user.getUserType().equals("Client")){
                    logger.info("Updated Successfully");
                    clientRepo.save(new Client(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassWord(), user.getAddress(),user.getUserType(),0f, 0f));
                    return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
                }else if(user.getUserType().equals("Coiffure")){
                    logger.info("Updated Successfully");
                    coiffureRepo.save(new Coiffure(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassWord(), user.getAddress(),user.getUserType(),false, false));
                    return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("Client Not found",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generateUserId(){
       return  RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());
    }
}

