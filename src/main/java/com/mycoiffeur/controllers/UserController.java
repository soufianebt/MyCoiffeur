package com.mycoiffeur.controllers;

import com.mycoiffeur.modele.Client;
import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.modele.User;
import com.mycoiffeur.repository.ClientRepo;
import com.mycoiffeur.repository.CoiffeurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private CoiffeurRepo coiffeurRepo;
    @Autowired
    private ClientRepo clientRepo;

    @GetMapping(value = "/")
    public String helloUser(){

        return "Hello";
    }
    @PostMapping(value = "/SignUp")
    public ResponseEntity<String> singUp(@RequestBody User user){
        try{
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffeurRepo.findByEmail(user.getEmail()).orElse(null));
            Optional<Client> client = Optional.ofNullable(clientRepo.findByEmail(user.getEmail()).orElse(null));
            if(coiffure.isPresent() || client.isPresent()){

                        return new ResponseEntity<>("user already exist",HttpStatus.ALREADY_REPORTED);
            }
            if(user.getUserType().equals("Client")){

                clientRepo.save(new Client(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassWord(), user.getAddress(),user.getUserType(),0f, 0f));
            }else if(user.getUserType().equals("Coiffure")){
                coiffeurRepo.save(new Coiffure(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassWord(), user.getAddress(),user.getUserType(),false, false));
            }else{
                return new ResponseEntity<>("Please specify the type",HttpStatus.EXPECTATION_FAILED);
            }

        return new ResponseEntity<>("Created Successfully",HttpStatus.OK);
    }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/SignUp/{email}")
    public ResponseEntity<User> singUp(@PathVariable String email) {
        try {
            Optional<Client> client = Optional.ofNullable(clientRepo.findByEmail(email).orElse(null));
            Optional<Coiffure> coiffure = Optional.ofNullable(coiffeurRepo.findByEmail(email).orElse(null));
            if (client.isPresent()) {
                return new ResponseEntity<>(client.get(), HttpStatus.OK);
            }else if(coiffure.isPresent()){

                return new ResponseEntity<>(coiffure.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

