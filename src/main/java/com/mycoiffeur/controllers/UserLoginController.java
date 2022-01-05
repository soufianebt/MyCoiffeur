package com.mycoiffeur.controllers;

import com.mycoiffeur.modele.User;
import com.mycoiffeur.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserLoginController {
@Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/")
    public String helloUser(){

        return "Hello";
    }
    @PostMapping(value = "/SignUp")
    public ResponseEntity<String> singUp(@RequestBody User user){
        try{
            //TODO : check user if existed
            userRepo.save(user);
        return new ResponseEntity<>("Created Successfully",HttpStatus.OK);
    }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/SignUp/{id}")
    public ResponseEntity<User> singUp(@PathVariable String id){
        try {
            User user ;
            user = userRepo.findUserByUserId(id).get();
            if (user != null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
