package com.mycoiffeur.controllers;

import com.mycoiffeur.modele.*;
import com.mycoiffeur.recommendation.ScriptRun;
import com.mycoiffeur.repository.ClientRepo;
import com.mycoiffeur.repository.CoiffureRepo;
import com.mycoiffeur.repository.ProfileRepo;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private CoiffureRepo coiffureRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ProfileRepo profileRepo;

        Logger logger =  LoggerFactory.getLogger(UserController.class);


    @RequestMapping("/")
    public String firstPage() throws IOException {
        return "Hello " +
                "Go To <a href=\"./swagger-ui.html\"> Link </a> To see documentation ";
    }

    @PostMapping(value = "/SignUp")
    public ResponseEntity<String> singUp(@RequestBody User user){
        try{
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findByEmail(user.getEmail()).orElse(null));
            Optional<Client> client = Optional.ofNullable(clientRepo.findByEmail(user.getEmail()).orElse(null));
            String userId = generateUserId();
            if(coiffure.isPresent() || client.isPresent()){
                        return new ResponseEntity<>("user already exist",HttpStatus.ALREADY_REPORTED);
            }
            if(user.getUserType().equals(UserType.CLIENT)){
                clientRepo.save(new Client(userId, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassWord(), user.getAddress(),UserType.CLIENT,0f, 0f));
            }else if(user.getUserType().equals(UserType.COIFFURE)){
                coiffureRepo.save(new Coiffure(userId, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassWord(), user.getAddress(),UserType.COIFFURE,false, false,"","",""));
                Profile profile = new Profile();
                profile.setUserId(userId);
                profileRepo.save(profile);
            }else{
                logger.info("Unspecified type COIFFURE, CLIENT");
                return new ResponseEntity<>("Unspecified type COIFFURE, CLIENT",HttpStatus.EXPECTATION_FAILED);
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
    public ResponseEntity<User> signIn(@RequestBody LoginIdentifier loginIdentifier){

        try {
            Optional<Client> client = Optional.ofNullable(clientRepo.findByEmail(loginIdentifier.getEmail()).orElse(null));
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findByEmail(loginIdentifier.getEmail()).orElse(null));
            if (client.isPresent()) {
                if(loginIdentifier.getPassWord().equals(client.get().getPassWord())){
                    logger.info("Client found");
                    Client modifiedClient = client.get();
                    modifiedClient.setPassWord("");
                    return new ResponseEntity<>(modifiedClient, HttpStatus.OK);
                }else{
                    logger.warn("Client Unauthorized");
                    return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
                }
            }else if(coiffure.isPresent()){
                if(loginIdentifier.getPassWord().equals(coiffure.get().getPassWord())) {
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


    @PutMapping(value = "/UserUpdate")
    public ResponseEntity<String> UpdateUser(@RequestBody User user) {
        try {
            if(user.getUserId() == null){
                return new ResponseEntity<>("You must Specified UserId",HttpStatus.NO_CONTENT);
            }
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffureRepo.findById(user.getUserId()).orElse(null));
            Optional<Client> client = Optional.ofNullable(clientRepo.findById(user.getUserId()).orElse(null));
            if (coiffure.isPresent() || client.isPresent()) {
                if(client.isPresent()){
                    logger.info("Updated Successfully");
                    Client client1 = client.get();
                    client1.setAddress(user.getAddress());
                    client1.setFirstName(user.getFirstName());
                    client1.setLastName(user.getLastName());
                    clientRepo.save(client1);
                    return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
                }else if(coiffure.isPresent()){
                    Coiffure coiffure1 = coiffure.get();
                    coiffure1.setFirstName(user.getFirstName());
                    coiffure1.setLastName(user.getLastName());
                    coiffure1.setAddress(user.getAddress());
                    logger.info("Updated Successfully");
                    coiffureRepo.save(coiffure1);
                    return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
                }else{
                    logger.info("Specified type COIFFURE, CLIENT");
                    return new ResponseEntity<>("Specified type COIFFURE, CLIENT",HttpStatus.NOT_FOUND);
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

