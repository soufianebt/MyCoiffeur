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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String HelloUser(){

        return "Hello";
    }
    @RequestMapping(value = "/SignUp", method = RequestMethod.POST)
    public void SingUp(@RequestBody User user){
        userRepo.save(user);
    }

    @RequestMapping(value = "/SignUp/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> SingUp(@PathVariable String id){
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
